import sys

sys.path.append('/')

import os

os.environ.setdefault("DJANGO_SETTINGS_MODULE", "PCGG_data.settings")

import django

django.setup()

import re
import time
from crawlers.models import Case, PriceHistory
from selenium.webdriver.common.by import By
from django.utils import timezone
from crawlers.get_parts.tools.tools import get_driver, get_product_list, save_current_page, move_to_next_page, \
    upload_to_storage, update_history, extract_product_info, update_database, get_name_and_price


def get_case_list(url: str):
    print("case crawling 시작")
    global case_info
    driver = get_driver(url)

    # 지원 보드 규격 클릭
    driver.find_element(
        By.CSS_SELECTOR, "#simpleSearchOptionpriceCompare > div > dl:nth-child(6) > dd > div > button.btn_spec_view.btn_view_more"
    ).click()

    # extended atx, standard atx, micro atx, mini itx
    attribute_value = ["22394", "22391", "22392", "22398"]
    for option in attribute_value:
        driver.find_element(
            By.CSS_SELECTOR, f"#searchAttributeValue{option}"
        ).click()


    # 잠시대기
    time.sleep(2)

    # 현재 페이지 번호 저장
    current_page = save_current_page(driver)

    # DB에 있는 case 리스트
    existing_cases = set(Case.objects.values_list('name', flat=True))

    # 크롤링한 case 목록
    crawled_cases = []

    # case list 추출 및 개별 case정보 추출
    while True:
        # case list 추출
        product_list = get_product_list(driver)

        # 개별 case 정보 추출
        for case in product_list:

            # 파싱 전 이름, 가격, 디테일 페이지 추출
            name, price, detail_page = get_name_and_price(case)

            # 이름 파싱
            # 파일명으로 사용할 수 없는 문자를 언더스코어로 대체
            invalid_characters = r'[\/:*?"<>|]'
            parsed_name = re.sub(invalid_characters, '_', name)
            parsed_name = parsed_name.replace(" ", "_")

            # 업로드한 이미지 주소
            file_url = upload_to_storage(detail_page, "case", parsed_name)

            # 만약 크롤링한 데이터에도 있고 DB에도 있는 데이터라면
            try:
                update_history(Case, parsed_name, price, "chassis")

            # 그렇지 않고 크롤링한 데이터에는 있고 db에 없는 데이터라면
            except:
                spec_items = extract_product_info(case)

                # 모델 저장에 필요한 변수들
                extended_atx = False
                standard_atx = False
                micro_atx = False
                mini_itx = False
                width = 0
                depth = 0
                max_power_depth = 0
                max_gpu_depth = 0
                max_cooler_depth = 0

                # 데이터 파싱해서 변수에 저장
                for item in spec_items:
                    try:
                        if item == "Extended-ATX":
                            extended_atx = True

                        if item == "표준-ATX":
                            standard_atx = True

                        if item == "Micro-ATX":
                            micro_atx = True

                        if item == "Mini-ITX":
                            mini_itx = True

                        if "너비" in item:
                            width = float(item.split()[1].replace("mm", ""))

                        if "깊이" in item:
                            depth = float(item.split()[1].replace("mm", ""))

                        if "파워 장착" in item:
                            if "~" in item.split()[2]:
                                max_power_depth = float(item.split()[2].split("~")[1].replace("mm", ""))

                            elif "최대" in item:
                                max_power_depth = float(item.split()[3].replace("mm", ""))

                            else:
                                max_power_depth = float(item.split()[2].replace("mm", ""))

                        if "GPU 장착" in item:
                            if "~" in item.split()[2]:
                                max_gpu_depth = float(item.split()[2].split("~")[1].replace("mm", ""))

                            elif "최대" in item:
                                max_gpu_depth = float(item.split()[3].replace("mm", ""))

                            else:
                                max_gpu_depth = float(item.split()[2].replace("mm", ""))

                        if "CPU쿨러 장착" in item:
                            if "~" in item.split()[2]:
                                max_cooler_depth = float(item.split()[2].split("~")[1].replace("mm", ""))

                            elif "최대" in item.split()[2]:
                                max_cooler_depth = float(item.split()[3].replace("mm", ""))

                            else:
                                max_cooler_depth = float(item.split()[2].replace("mm", ""))

                    except Exception as e:
                        print(e, "\n")
                        print(item, "\n")

                # case 모델 업데이트
                try:
                    case_info = Case(
                        name=parsed_name,
                        price=price,
                        image_source=file_url,
                        extinct=False,
                        extended_atx=extended_atx,
                        standard_atx=standard_atx,
                        micro_atx=micro_atx,
                        mini_itx=mini_itx,
                        width=width,
                        depth=depth,
                        max_power_depth=max_power_depth,
                        max_gpu_depth=max_gpu_depth,
                        max_cooler_depth=max_cooler_depth
                    )
                    case_info.save()

                except Exception as e:
                    print(e, "\n")

                try:
                    # 가격 추적 모델도 업데이트
                    price_history = PriceHistory(
                        type="chassis",
                        part_id=case_info.id,
                        changed_date=timezone.now(),
                        price=price
                    )
                    price_history.save()

                except Exception as e:
                    print(e, "\n")

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
        update_database(crawled_cases, existing_cases, Case)

    except Exception as e:
        print(e, "\n")

    print("case crawling 끝")
    driver.quit()


get_case_list("https://prod.danawa.com/list/?cate=112775")