import sys

sys.path.append('/')

import os

os.environ.setdefault("DJANGO_SETTINGS_MODULE", "PCGG_data.settings")

import django

django.setup()

import re
import time
from crawlers.models import Ssd, PriceHistory
from selenium.webdriver.common.by import By
from django.utils import timezone
from crawlers.get_parts.tools.tools import get_driver, get_product_list, save_current_page, move_to_next_page, \
    upload_to_storage, update_history, extract_product_info, update_database, get_name_and_price


def get_ssd_list(url: str):
    print("ssd 크롤링 시작")
    global ssd_info
    driver = get_driver(url)

    # 2280, pcie5, pcie4, pcie3
    filter_options = ["202347", "859759", "402191", "213230"]
    for option in filter_options:
        driver.find_element(
            By.CSS_SELECTOR, f"input#searchAttributeValueRep{option}"
        ).click()


    # 잠시대기
    time.sleep(2)

    # 현재 페이지 번호 저장
    current_page = save_current_page(driver)

    # DB에 있는 ssd 리스트
    existing_ssds = set(Ssd.objects.values_list('name', flat=True))

    # 크롤링한 ssd 목록
    crawled_ssds = []

    # ssd list 추출 및 개별 ssd정보 추출
    while True:
        # ssd list 추출
        product_list = get_product_list(driver)

        # 개별 ssd 정보 추출
        for ssd in product_list:

            # 파싱 전 이름, 가격, 디테일 페이지 추출
            name, price, detail_page = get_name_and_price(ssd)

            # 제조사 추출
            try:
                manufacturer = name.split()[0]

            except Exception as e:
                print(e)
                print(name)
                manufacturer = "unknown"

            # 용량 추출
            try:
                capacity = int(re.search(r'\d+', name.split()[-1]).group())
                # 용량이 GB 단위인 경우 TB 단위로 변환
                if "GB" in name:
                    capacity = capacity / 1000

                elif "TB" in name:
                    capacity = capacity

            except Exception as e:
                print(e)
                print(name)
                capacity = 0

            # 이름 파싱
            # 파일명으로 사용할 수 없는 문자를 언더스코어로 대체
            invalid_characters = r'[\/:*?"<>|]'
            parsed_name = re.sub(invalid_characters, '_', name)
            parsed_name = parsed_name.replace(" ", "_")

            # 업로드한 이미지 주소
            file_url = upload_to_storage(detail_page, "ssd", parsed_name)

            # 만약 크롤링한 데이터에도 있고 DB에도 있는 데이터라면
            try:
                update_history(Ssd, parsed_name, price, "ssd")

            # 그렇지 않고 크롤링한 데이터에는 있고 db에 없는 데이터라면
            except:
                spec_items = extract_product_info(ssd)

                # 모델 저장에 필요한 변수들
                pcie_ver = 0
                reading_speed = 0
                writing_speed = 0

                # 데이터 파싱해서 변수에 저장
                for item in spec_items:
                    try:
                        # pcie 버전 추출
                        # 딕셔너리를 사용하여 PCIe 버전을 매핑
                        pcie_versions = {"PCIe5": 5, "PCIe4": 4, "PCIe3": 3}

                        # item에서 PCIe 버전을 찾아 pcie_ver에 할당
                        for key, value in pcie_versions.items():
                            if key in item:
                                pcie_ver = value

                        # 읽기 속도 추출
                        if "순차읽기" in item:
                            reading_speed = int(item.split()[2].replace("MB/s", "").replace(",", ""))

                        elif "순차쓰기" in item:
                            writing_speed = int(item.split()[1].replace("MB/s", "").replace(",", ""))

                    except Exception as e:
                        print(e)
                        print(item)

                # ssd 모델 업데이트
                try:
                    ssd_info = Ssd(
                        name=parsed_name,
                        price=price,
                        pcie_ver=pcie_ver,
                        reading_speed=reading_speed,
                        writing_speed=writing_speed,
                        capacity=capacity,
                        manufacturer=manufacturer,
                        image_source=file_url,
                        extinct=False
                    )
                    ssd_info.save()

                except Exception as e:
                    print(e)

                try:
                    # 가격 추적 모델도 업데이트
                    price_history = PriceHistory(
                        type="ssd",
                        part_id=ssd_info.id,
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
        update_database(crawled_ssds, existing_ssds, Ssd)

    except Exception as e:
        print(e)

    print("ssd 크롤링 끝")
    driver.quit()


get_ssd_list("https://prod.danawa.com/list/?cate=112760")