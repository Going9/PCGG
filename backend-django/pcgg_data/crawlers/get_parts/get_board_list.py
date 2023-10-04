import sys

sys.path.append('/')

import os

os.environ.setdefault("DJANGO_SETTINGS_MODULE", "PCGG_data.settings")

import django

django.setup()

import re
import time
from crawlers.models import Board, PriceHistory
from selenium.webdriver.common.by import By
from django.utils import timezone
from crawlers.get_parts.tools.tools import get_driver, get_product_list, save_current_page, move_to_next_page, \
    upload_to_storage, update_history, update_database, get_name_and_price


def get_board_list(url: str):
    print("board_list 시작")
    driver = get_driver(url)

    filter_options = ["748870", "801682", "212831"]
    for option in filter_options:
        driver.find_element(
            By.CSS_SELECTOR, f"input#searchAttributeValueRep{option}"
        ).click()

    other_options = ["2459", "2460", "2464"]
    for option in other_options:
        driver.find_element(
            By.CSS_SELECTOR, f"#searchAttributeValue{option}"
        ).click()

    # 잠시대기
    time.sleep(2)

    # 현재 페이지 번호 저장
    current_page = save_current_page(driver)

    # DB에 있는 cpu 리스트
    existing_boards = set(Board.objects.values_list('name', flat=True))

    # 크롤링한 board 목록
    crawled_boards = []

    # board list 추출 및 개별 board정보 추출
    while True:
        # board list 추출
        product_list = get_product_list(driver)

        # 개별 board 정보 추출
        for board in product_list:

            # 파싱 전 이름, 가격, 디테일 페이지 추출
            name, price, detail_page = get_name_and_price(board)

            # 이름 파싱
            # 파일명으로 사용할 수 없는 문자를 언더스코어로 대체
            invalid_characters = r'[\/:*?"<>|]'
            parsed_name = re.sub(invalid_characters, '_', name)
            parsed_name = parsed_name.replace(" ", "_")

            file_url = upload_to_storage(detail_page, "board", parsed_name)

            # 만약 크롤링한 데이터에도 있고 DB에도 있는 데이터라면
            try:
                update_history(Board, parsed_name, price, "mainboard")

            # 그렇지 않고 크롤링한 데이터에는 있고 db에 없는 데이터라면
            except:
                # /로 구분된 spec_list 추출
                spec_list = board.find_element(By.CSS_SELECTOR, ".spec_list").text

                # spec_list 재가공
                spec_items = spec_list.split(" /")
                spec_items = [item.strip() for item in spec_items]

                # 모델 저장에 필요한 변수들
                socket_info = ''
                grade = ''
                memory_spec = ''
                size = ''
                pcie3 = False
                pcie4 = False
                pcie5 = False
                m2_count = 0

                # 데이터 파싱해서 변수에 저장
                try:
                    for item in spec_items:
                        if "소켓" in item:
                            socket_info = item.split("소켓")[1].strip().rstrip(')').strip()

                        elif "AMD" in item:
                            amd_chipsets = {
                                "X670E": "X670E",
                                "X670": "X670",
                                "B650E": "B650E",
                                "B650": "B650",
                                "A620": "A620",
                                "A320": "A320",
                                "B350": "B350",
                                "B450": "B450",
                                "X370": "X370",
                                "X470": "X470",
                                "X450": "X450",
                                "X570": "X570",
                                "B550": "B550",
                                "A520": "A520"
                            }
                            chipset = item.split()[1]
                            grade = amd_chipsets.get(chipset, None)

                        elif "인텔" in item:
                            intel_chipsets = {
                                "H610": "H610",
                                "B660": "B660",
                                "H670": "H670",
                                "Z690": "Z690",
                                "B760": "B760",
                                "H770": "H770",
                                "Z790": "Z790"
                            }

                            chipset = item.split()[1]
                            grade = intel_chipsets.get(chipset, None)

                        elif ("ATX" in item or
                              "M-ATX" in item or
                              "M-iTX" in item):
                            size = item.split()[0]

                        elif "DDR4" in item:
                            memory_spec = "DDR4"

                        elif "DDR5" in item:
                            memory_spec = "DDR5"

                        if "PCIe" in item:
                            if "PCIe5.0" in item:
                                pcie5 = True

                            if "PCIe4.0" in item:
                                pcie4 = True

                            if "PCIe3.0" in item:
                                pcie3 = True

                        if "M.2:" in item:
                            m2_count = item.split()[2][0]

                except Exception as e:
                    print(e, f"{item} \n")

                try:
                    # board 모델 업데이트
                    board_info = Board(
                        name=parsed_name,
                        price=price,
                        socket_info=socket_info,
                        grade=grade,
                        memory_spec=memory_spec,
                        size=size,
                        pcie3=pcie3,
                        pcie4=pcie4,
                        pcie5=pcie5,
                        m2_count=m2_count,
                        image_source=file_url,
                        extinct=False
                    )
                    board_info.save()

                    # 가격 추적 모델도 업데이트
                    price_history = PriceHistory(
                        type="mainboard",
                        part_id=board_info.id,
                        changed_date=timezone.now(),
                        price=price
                    )
                    price_history.save()

                except Exception as e:
                    print(e, f"{parsed_name} \n")


        # 스크롤 끝까지 내리고
        driver.execute_script(
            "window.scrollTo(0, document.body.scrollHeight);"
        )
        time.sleep(2)

        # 다음 페이지로 이동
        success, current_page = move_to_next_page(driver, current_page)
        if not success:
            break

    update_database(crawled_boards, existing_boards, Board)

    print("board_list 끝")
    driver.quit()


board_url = "https://prod.danawa.com/list/?cate=112751&15main_11_02"
get_board_list(board_url)
