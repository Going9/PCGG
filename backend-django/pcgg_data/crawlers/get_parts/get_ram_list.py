import sys

sys.path.append('/')

import os

os.environ.setdefault("DJANGO_SETTINGS_MODULE", "PCGG_data.settings")

import django

django.setup()

import re
import time
import datetime
from crawlers.models import PriceHistory, Ram
from selenium.webdriver.common.by import By
from django.utils import timezone
from crawlers.get_parts.tools.tools import get_driver, get_product_list, save_current_page, move_to_next_page, \
    upload_to_storage, update_history, extract_product_info, update_database, get_name_and_price


def get_ram_list(url: str):
    print("램 크롤링 시작")
    driver = get_driver(url)

    # 데스크탑, DDR5, DDR4
    other_options = ["1223", "748099", "164333"]
    for option in other_options:
        driver.find_element(
            By.CSS_SELECTOR, f"#searchAttributeValue{option}"
        ).click()

    # 잠시대기
    time.sleep(2)

    # 현재 페이지 번호 저장
    current_page = save_current_page(driver)

    # DB에 있는 Ram 리스트
    existing_rams = set(Ram.objects.values_list('name', flat=True))

    # 크롤링한 ram 목록
    crawled_rams = []

    # ram list 추출 및 개별 ram정보 추출
    while True:
        # ram list 추출
        product_list = get_product_list(driver)

        # 개별 ram 정보 추출
        for ram in product_list:
            # 파싱 전 이름, 가격, 디테일 페이지 추출
            name, price, detail_page = get_name_and_price(ram)

            # 이름 파싱해서 램 용량 추출
            # 1개: (4GB), (8GB), (16GB), (32GB)
            # 2개: (16GB(8Gx2)), (32GB(16Gx2)), (48GB(24Gx2)), (64GB(32Gx2)),
            # 4개: (32GB(8Gx4)), (64GB(16Gx4)), (128GB(32Gx4)), (192GB(48Gx4))
            # 8개: (64GB(8Gx8)), (128GB(16Gx8))
            ram_capacity_set = {
                "(4GB)": 4,
                "(8GB)": 8,
                "(16GB)": 16,
                "(32GB)": 32,
                "(16GB(8Gx2))": 16,
                "(32GB(16Gx2))": 32,
                "(48GB(24Gx2))": 48,
                "(64GB(32Gx2))": 64,
                "(32GB(8Gx4))": 32,
                "(64GB(16Gx4))": 64,
                "(128GB(32Gx4))": 128,
                "(192GB(48Gx4))": 192,
                "(64GB(8Gx8))": 64,
                "(128GB(16Gx8))": 128,
            }

            ram_capacity = 0
            for name_item in name.split():
                try:
                    ram_capacity = ram_capacity_set.get(name_item, None)

                except Exception as e:
                    print(e)
                    ram_capacity = None

            # 파일명으로 사용할 수 없는 문자를 언더스코어로 대체
            invalid_characters = r'[\/:*?"<>|]'
            parsed_name = re.sub(invalid_characters, '_', name)
            parsed_name = parsed_name.replace(" ", "_")

            # 업로드한 이미지 주소
            file_url = upload_to_storage(detail_page, "ram", parsed_name)

            # 만약 크롤링한 데이터에도 있고 DB에도 있는 데이터라면
            try:
                update_history(Ram, parsed_name, price, "ram")

            # 그렇지 않고 크롤링한 데이터에는 있고 db에 없는 데이터라면
            except Ram.DoesNotExist:
                spec_items = extract_product_info(ram)

                # 모델 저장에 필요한 변수들
                memory_spec = ''
                memory_clock = 0
                heat_sink = False

                try:
                    # 데이터 파싱해서 변수에 저장
                    for item in spec_items:
                        if "DDR4" in item:
                            memory_spec = "DDR4"

                        elif "DDR5" in item:
                            memory_spec = "DDR5"

                        if "MHz" in item:
                            memory_clock = int(item.split("MHz")[0].strip())

                        elif "히트싱크" in item:
                            if "방열판" in item:
                                heat_sink = True

                except Exception as e:
                    print(e)


                try:
                    # ram 모델 업데이트
                    ram_info = Ram(
                        name=parsed_name,
                        price=price,
                        memory_spec=memory_spec,
                        memory_clock=memory_clock,
                        heat_sink=heat_sink,
                        capacity=ram_capacity,
                        image_source=file_url,
                        extinct=False
                    )
                    ram_info.save()

                    # 가격 추적 모델도 업데이트
                    price_history = PriceHistory(
                        type="ram",
                        part_id=ram_info.id,
                        changed_date=timezone.now(),
                        price=price
                    )
                    price_history.save()

                except Exception as e:
                    print(e, "ram model update error")

        # 스크롤 끝까지 내리고
        driver.execute_script(
            "window.scrollTo(0, document.body.scrollHeight);"
        )
        time.sleep(2)

        # 다음 페이지로 이동
        success, current_page = move_to_next_page(driver, current_page)
        if not success:
            break

    update_database(crawled_rams, existing_rams, Ram)

    print("램 크롤링 완료")
    driver.quit()


get_ram_list("https://prod.danawa.com/list/?cate=112752")