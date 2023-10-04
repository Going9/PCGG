import sys

sys.path.append('/')

import os

os.environ.setdefault("DJANGO_SETTINGS_MODULE", "PCGG_data.settings")

import django

django.setup()

import re
import time
from crawlers.models import Gpu, PriceHistory
from selenium.webdriver.common.by import By
from django.utils import timezone
from crawlers.get_parts.tools.tools import get_driver, get_product_list, save_current_page, move_to_next_page, \
    upload_to_storage, update_history, extract_product_info, update_database, get_name_and_price


def get_gpu_list(url: str):
    print("gpu 크롤링 시작")
    global gpu_info
    driver = get_driver(url)

    # 코드 네임 전체 보기 클릭
    driver.find_element(
        By.CSS_SELECTOR, "#frmProductList > div.option_nav > div.nav_header > div.head_opt > button").click()

    # 제품 시리즈 확장
    driver.find_element(
        By.CSS_SELECTOR, "#extendSearchOptionpriceCompare > div > dl:nth-child(5) > dd > div > button.btn_spec_view.btn_view_more"
    ).click()

    # rtx 40, rtx 30, gtx 16, gtx10, rx60, rx70
    other_options = ["805756", "693445", "335752", "197695", "818830", "708844"]
    for option in other_options:
        driver.find_element(
            By.CSS_SELECTOR, f"#searchAttributeValue{option}"
        ).click()

    # 잠시대기
    time.sleep(2)

    # 현재 페이지 번호 저장
    current_page = save_current_page(driver)

    # DB에 있는 gpu 리스트
    existing_gpus = set(Gpu.objects.values_list('name', flat=True))

    # 크롤링한 gpu 목록
    crawled_gpus = []

    # gpu list 추출 및 개별 gpu정보 추출
    while True:
        # gpu list 추출
        product_list = get_product_list(driver)

        # 개별 gpu 정보 추출
        for gpu in product_list:

            # 파싱 전 이름, 가격, 디테일 페이지 추출
            name, price, detail_page = get_name_and_price(gpu)

            # gpu set
            gpu_set = {
                "1030": 2495,
                "1050": 6299,
                "1060": 10075,
                "1070": 14711,
                "1080": 18509,
                "1650": 7853,
                "1660": 12953,
                "3060": 20639,
                "3070": 22505,
                "3080": 25422,
                "3090": 26954,
                "4060": 22269,
                "4070": 26985,
                "4080": 35169,
                "4090": 39176,
                "6400": 7509,
                "6500": 9502,
                "6600": 16461,
                "6700": 19914,
                "6800": 25133,
                "6900": 27014,
                "6950": 28915,
                "6750": 21337,
                "6650": 17701,
                "7600": 16378,
                "7700": 21000,
                "7800": 27000,
                "7900": 31800,
            }

            score = 0
            # 점수 추출
            for name_item in name.split():
                if name_item in gpu_set:
                    score = gpu_set.get(name_item, None)

                if "TI" in name_item:
                    score = score * 1.1

                # 4070ti 특별 처리
                elif "TI" in name_item and "4070" in name_item:
                    score = 32000

                if "SUPER" in name_item:
                    score = score * 1.2

                if "OC" in name_item:
                    score = score * 1.05

            # 이름 파싱
            # 파일명으로 사용할 수 없는 문자를 언더스코어로 대체
            invalid_characters = r'[\/:*?"<>|]'
            parsed_name = re.sub(invalid_characters, '_', name)
            parsed_name = parsed_name.replace(" ", "_")

            # 업로드한 이미지 주소
            file_url = upload_to_storage(detail_page, "gpu", parsed_name)

            # 만약 크롤링한 데이터에도 있고 DB에도 있는 데이터라면
            try:
                update_history(Gpu, parsed_name, price, "gpu")

            # 그렇지 않고 크롤링한 데이터에는 있고 db에 없는 데이터라면
            except:
                spec_items = extract_product_info(gpu)

                # 모델 저장에 필요한 변수들
                needed_power = 0
                width = 0
                thickness = 0

                # 데이터 파싱해서 변수에 저장
                for item in spec_items:
                    try:
                        if "정격파워" in item:
                            needed_power = int(item.split()[1].replace("W", ""))

                        elif "가로" in item:
                            width = float(item.split()[1].replace("mm", ""))

                        elif "두께" in item:
                            if "~" in item.split()[1]:
                                thickness = float(item.split()[1].split("~")[1].replace("mm", ""))

                            else:
                                thickness = float(item.split()[1].replace("mm", ""))

                    except Exception as e:
                        print(e)
                        print(item)

                # gpu 모델 업데이트
                try:
                    gpu_info = Gpu(
                        name=parsed_name,
                        price=price,
                        needed_power=needed_power,
                        width=width,
                        thickness=thickness,
                        image_source=file_url,
                        score=score,
                        extinct=False
                    )
                    gpu_info.save()

                except Exception as e:
                    print(e)

                try:
                    # 가격 추적 모델도 업데이트
                    price_history = PriceHistory(
                        type="gpu",
                        part_id=gpu_info.id,
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
        update_database(crawled_gpus, existing_gpus, Gpu)

    except Exception as e:
        print(e)

    print("gpu 크롤링 종료")
    driver.quit()


get_gpu_list("https://prod.danawa.com/list/?cate=112753")
