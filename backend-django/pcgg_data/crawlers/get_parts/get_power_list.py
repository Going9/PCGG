import sys

sys.path.append('/')

import os

os.environ.setdefault("DJANGO_SETTINGS_MODULE", "PCGG_data.settings")

import django

django.setup()

import re
import time
from crawlers.models import Power, PriceHistory
from selenium.webdriver.common.by import By
from django.utils import timezone
from crawlers.get_parts.tools.tools import get_driver, get_product_list, save_current_page, move_to_next_page, \
    upload_to_storage, update_history, extract_product_info, update_database, get_name_and_price


def get_power_list(url: str):
    print("power 크롤링 시작")
    global power_info
    driver = get_driver(url)

    # atx, matx, tfx
    attribute_value = ["5529", "5530", "5531"]
    for option in attribute_value:
        driver.find_element(
            By.CSS_SELECTOR, f"#searchAttributeValue{option}"
        ).click()


    # 잠시대기
    time.sleep(2)

    # 현재 페이지 번호 저장
    current_page = save_current_page(driver)

    # DB에 있는 power 리스트
    existing_powers = set(Power.objects.values_list('name', flat=True))

    # 크롤링한 power 목록
    crawled_powers = []

    # grade_set
    grade_set = {
        "80 PLUS 티타늄": "titanium",
        "80 PLUS 플래티넘": "platinum",
        "80 PLUS 골드": "gold",
        "80 PLUS 실버": "silver",
        "80 PLUS 브론즈": "bronze",
        "80 PLUS 스탠다드": "standard",
    }

    # power list 추출 및 개별 power정보 추출
    while True:
        # power list 추출
        product_list = get_product_list(driver)

        # 개별 power 정보 추출
        for power in product_list:

            # 파싱 전 이름, 가격, 디테일 페이지 추출
            name, price, detail_page = get_name_and_price(power)

            # 이름 파싱
            # 파일명으로 사용할 수 없는 문자를 언더스코어로 대체
            invalid_characters = r'[\/:*?"<>|]'
            parsed_name = re.sub(invalid_characters, '_', name)
            parsed_name = parsed_name.replace(" ", "_")

            # 업로드한 이미지 주소
            file_url = upload_to_storage(detail_page, "power", parsed_name)

            # 만약 크롤링한 데이터에도 있고 DB에도 있는 데이터라면
            try:
                update_history(Power, parsed_name, price, "power")

            # 그렇지 않고 크롤링한 데이터에는 있고 db에 없는 데이터라면
            except:
                spec_items = extract_product_info(power)

                # 모델 저장에 필요한 변수들
                size = ""
                grade = ""
                output = ""
                depth = 0
                free_warranty_period = 0

                # 데이터 파싱해서 변수에 저장
                for item in spec_items:
                    try:
                        if item == spec_items[0]:
                            size = item.split()[0]

                        elif "정격출력" in item:
                            output = item.split()[1].replace("W", "")

                        elif "80 PLUS" in item:
                            grade = grade_set.get(item, None)

                        elif "깊이" in item:
                            depth = float(item.split()[1].replace("mm", ""))

                        elif "무상" in item and "연장" not in item:
                            free_warranty_period = int(item.split()[1].replace("년", ""))

                    except Exception as e:
                        print(e)
                        print(item)

                # power 모델 업데이트
                try:
                    power_info = Power(
                        name=parsed_name,
                        price=price,
                        image_source=file_url,
                        extinct=False,
                        size=size,
                        grade=grade,
                        output=output,
                        depth=depth,
                        free_warranty_period=free_warranty_period
                    )
                    power_info.save()

                except Exception as e:
                    print(e)

                try:
                    # 가격 추적 모델도 업데이트
                    price_history = PriceHistory(
                        type="power",
                        part_id=power_info.id,
                        changed_date=timezone.now(),
                        price=price
                    )
                    price_history.save()

                except Exception as e:
                    print(e)

        # 스크롤 끝까지 내리고
        driver.execute_script(
            "window.scrollTo(0, document.body.scrollHeight);"
        )
        time.sleep(2)

        # 다음 페이지로 이동
        success, current_page = move_to_next_page(driver, current_page)
        if not success:
            break

    try:
        update_database(crawled_powers, existing_powers, Power)

    except Exception as e:
        print(e)

    print("power 크롤링 종료")
    driver.quit()


get_power_list("https://prod.danawa.com/list/?cate=112777")