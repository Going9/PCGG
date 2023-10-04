import sys

sys.path.append('/')

import os

os.environ.setdefault("DJANGO_SETTINGS_MODULE", "PCGG_data.settings")

import django

django.setup()

import re
import time
from crawlers.models import Cooler, PriceHistory
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from django.utils import timezone
from crawlers.get_parts.tools.tools import get_driver, get_product_list, save_current_page, move_to_next_page, \
    upload_to_storage, update_history, extract_product_info, update_database, get_name_and_price


def get_cooler_list(url: str):
    print("쿨러 크롤링 시작")
    global cooler_info
    driver = get_driver(url)

    driver.implicitly_wait(10)
    attribute_value = ["29607", "865960", "29610", "29609"]
    for option in attribute_value:
        driver.find_element(
            By.CSS_SELECTOR, f"#searchAttributeValue{option}"
        ).click()

    # 옵션 전체 보기
    driver.find_element(
        By.CSS_SELECTOR, "#frmProductList > div.option_nav > div.nav_header > div.head_opt > button"
    ).click()

    attribute_value = ["743326", "776764", "213365"]
    for option in attribute_value:
        driver.find_element(
            By.CSS_SELECTOR, f"#searchAttributeValue{option}"
        ).click()

    # 현재 페이지 번호 저장
    current_page = save_current_page(driver)

    # DB에 있는 cooler 리스트
    existing_coolers = set(Cooler.objects.values_list('name', flat=True))

    # 크롤링한 cooler 목록
    crawled_coolers = []

    # grade_set
    form_set = {
        "수랭": "수랭",
        "싱글타워형": "싱글타워형",
        "듀얼타워형": "듀얼타워형",
        "일반형": "일반형",
        "슬림형": "슬림형",
    }

    # cooler list 추출 및 개별 cooler정보 추출
    while True:
        # cooler list 추출
        product_list = get_product_list(driver)

        # 개별 cooler 정보 추출
        for cooler in product_list:
            # 파싱 전 이름, 가격, 디테일 페이지 추출
            try:
                name, price, detail_page = get_name_and_price(cooler)

            except Exception as e:
                continue

            # 이름 파싱
            # 파일명으로 사용할 수 없는 문자를 언더스코어로 대체
            invalid_characters = r'[\/:*?"<>|]'
            parsed_name = re.sub(invalid_characters, '_', name)
            parsed_name = parsed_name.replace(" ", "_")

            # 업로드한 이미지 주소
            file_url = upload_to_storage(detail_page, "cooler", parsed_name)

            # 만약 크롤링한 데이터에도 있고 DB에도 있는 데이터라면
            try:
                update_history(Cooler, parsed_name, price, "cooler")

            # 그렇지 않고 크롤링한 데이터에는 있고 db에 없는 데이터라면
            except:
                # spec 정보 추출
                try:
                    spec_items = extract_product_info(cooler)

                except:
                    continue

                # 모델 저장에 필요한 변수들
                form = ""
                height = float()
                fan_count = int()
                max_fan_noise = float()
                free_warranty_period = 0

                # 데이터 파싱해서 변수에 저장
                for item in spec_items:
                    try:
                        if item in form_set:
                            form = form_set.get(item, None)

                        if "A/S" in item:
                            free_warranty_period = item.split()[1].replace("년", "")

                        elif "높이" in item:
                            height = float(item.split()[1].replace("mm", ""))

                        elif "팬 개수" in item:
                            fan_count = int(item.split()[2].replace("개", ""))

                        elif "최대 팬소음" in item:
                            max_fan_noise = float(item.split()[2].replace("dBA", ""))

                    except Exception as e:
                        print(e)
                        print(item)

                # cooler 모델 업데이트
                try:
                    cooler_info = Cooler(
                        name=parsed_name,
                        price=price,
                        image_source=file_url,
                        extinct=False,
                        form=form,
                        height=height,
                        fan_count=fan_count,
                        max_fan_noise=max_fan_noise,
                        free_warranty_period=free_warranty_period
                    )
                    cooler_info.save()

                except Exception as e:
                    print(e)

                try:
                    # 가격 추적 모델도 업데이트
                    price_history = PriceHistory(
                        type="cooler",
                        part_id=cooler_info.id,
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
        update_database(crawled_coolers, existing_coolers, Cooler)

    except Exception as e:
        print(e)

    print("쿨러 크롤링 끝")
    driver.quit()


get_cooler_list("https://prod.danawa.com/list/?cate=11236855")
