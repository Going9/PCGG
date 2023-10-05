import sys

sys.path.append('/')

import os

os.environ.setdefault("DJANGO_SETTINGS_MODULE", "PCGG_data.settings")

import django

django.setup()

import re
import time
from crawlers.models import Cpu, PriceHistory
from selenium.webdriver.common.by import By
from django.utils import timezone
from crawlers.get_parts.tools.tools import get_driver, get_product_list, save_current_page, move_to_next_page, \
    upload_to_storage, update_history, extract_product_info, update_database, get_name_and_price


def get_cpu_list(url: str):
    print("CPU 크롤링 시작")
    driver = get_driver(url)

    # 코드 네임 전체 보기 클릭
    driver.find_element(
        By.CSS_SELECTOR, ".spec_opt_view > button:first-child").click()

    # CPU 코드 네임 필터: 랩터, 엘더, 라파엘, 버미어, 세잔
    filter_options = ["807919", "748297", "801673", "706786", "740914"]
    for option in filter_options:
        driver.find_element(
            By.CSS_SELECTOR, f"input#searchAttributeValueRep{option}"
        ).click()

    # 르누아르, 르누아르x
    other_options = ["772063", "676055"]
    for option in other_options:
        driver.find_element(
            By.CSS_SELECTOR, f"#searchAttributeValue{option}"
        ).click()

    # 잠시대기
    time.sleep(2)

    # 현재 페이지 번호 저장
    current_page = save_current_page(driver)

    # DB에 있는 cpu 리스트
    existing_cpus = set(Cpu.objects.values_list('name', flat=True))

    # 크롤링한 cpu 목록
    crawled_cpus = []

    # cpu list 추출 및 개별 cpu정보 추출
    while True:
        # cpu list 추출
        product_list = get_product_list(driver)

        # 개별 cpu 정보 추출
        for cpu in product_list:

            # 파싱 전 이름, 가격, 디테일 페이지 추출
            name, price, detail_page = get_name_and_price(cpu)

            # 이름 파싱
            parsed_names = name.split()
            parsed_name = ''
            for parsed_data in parsed_names:
                # AMD나 INTEL로 시작하지 않고
                if not parsed_data[0].startswith(("AMD", "INTEL")):
                    # 숫자로 시작하거나 영어로 시작하는 데이터
                    if parsed_data[0].isdigit() or parsed_data[0].isalpha():
                        parsed_name = parsed_data

            # 업로드한 이미지 주소
            file_url = upload_to_storage(detail_page, "cpu", parsed_name)

            # 만약 크롤링한 데이터에도 있고 DB에도 있는 데이터라면
            try:
                update_history(Cpu, parsed_name, price, "cpu")
                print(f"{parsed_name} 업데이트 완료")

            # 그렇지 않고 크롤링한 데이터에는 있고 db에 없는 데이터라면
            except:
                spec_items = extract_product_info(cpu)

                # 모델 저장에 필요한 변수들
                socket_info = ''
                ddr5 = False
                ddr4 = False
                integrated_graphics = False
                cooler_included = False
                single_score = 0
                multi_score = 0

                # 데이터 파싱해서 변수에 저장
                for item in spec_items:
                    if "소켓" in item:
                        socket_info = item.split("소켓")[1].strip().rstrip(')').strip()

                    if "DDR4" in item:
                        ddr4 = True

                    if "DDR5" in item:
                        ddr5 = True

                    if "내장그래픽" in item and item.split()[1] == "탑재":
                        integrated_graphics = True

                    if "쿨러" in item and "미포함" not in item:
                        cooler_included = True

                    if "시네벤치R23(싱글):" in item:
                        single_match = re.search(r"시네벤치R23\(싱글\): ([\d,]+)", item)
                        if single_match:
                            single_score = int(
                                single_match.group(1).replace(',', '')
                            )

                    if "시네벤치R23(멀티):" in item:
                        multi_match = re.search(r"시네벤치R23\(멀티\): ([\d,]+)", item)
                        if multi_match:
                            multi_score = int(
                                multi_match.group(1).replace(',', '')
                            )

                # cpu 모델 업데이트
                cpu_info = Cpu(
                    name=parsed_name,
                    price=price,
                    socket_info=socket_info,
                    ddr4=ddr4,
                    ddr5=ddr5,
                    integrated_graphics=integrated_graphics,
                    cooler_included=cooler_included,
                    image_source=file_url,
                    single_score=single_score,
                    multi_score=multi_score,
                    extinct=False
                )
                cpu_info.save()

                # 가격 추적 모델도 업데이트
                price_history = PriceHistory(
                    type="cpu",
                    part_id=cpu_info.id,
                    changed_date=timezone.now(),
                    price=price
                )
                price_history.save()
                print(f"{parsed_name} 저장 완료")

        # 스크롤 끝까지 내리고
        driver.execute_script(
            "window.scrollTo(0, document.body.scrollHeight);"
        )
        time.sleep(2)

        # 다음 페이지로 이동
        success, current_page = move_to_next_page(driver, current_page)
        if not success:
            break

    update_database(crawled_cpus, existing_cpus, Cpu)

    print("CPU 크롤링 종료")
    driver.quit()


get_cpu_list("https://prod.danawa.com/list/?cate=112747")
