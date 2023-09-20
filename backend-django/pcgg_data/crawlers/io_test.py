import os
from crawlers.models import Board, PriceHistory, Cpu
import requests
import time
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.service import Service as ChromeService
from django.utils import timezone
from crawlers.get_parts.tools.move_to_next_page import move_to_next_page
from crawlers.upload_s3 import upload_s3
import re
import threading


def io_test():
    def board():
        service = ChromeService(
            "C:/Users/ickyu/Downloads/chromedriver-win64/chromedriver-win64/chromedriver.exe")
        driver = webdriver.Chrome(service=service)
        driver.get("https://prod.danawa.com/list/?cate=112751&15main_11_02")
        driver.implicitly_wait(10)

        # CPU 소켓 선택
        filter_options = ["748870", "801682", "212831"]
        for option in filter_options:
            driver.find_element(
                By.CSS_SELECTOR, f"input#searchAttributeValueRep{option}"
            ).click()

        # 폼팩터 선택
        other_options = ["2459", "2460", "2464"]
        for option in other_options:
            driver.find_element(
                By.CSS_SELECTOR, f"#searchAttributeValue{option}"
            ).click()

        # 잠시대기
        time.sleep(2)

        # 현재 페이지 번호 저장
        current_page = int(driver.find_element(
            By.CSS_SELECTOR, ".num.now_on").text)

        # DB에 있는 board 리스트
        existing_boards = set(Board.objects.values_list('name', flat=True))

        # 크롤링한 board 목록
        crawled_boards = []

        # board list 추출 및 개별 board정보 추출
        while True:
            # board list 추출
            board_list = driver.find_elements(
                By.CSS_SELECTOR,
                ".main_prodlist.main_prodlist_list > .product_list .prod_item.prod_layer:not(.product-pot)"
            )

            # 개별 board 정보 추출
            for board in board_list:

                # 이름
                name = board.find_element(By.CSS_SELECTOR, ".prod_name a").text
                name = name.replace(" ", "_")
                parsed_name = name

                # 가격
                price = board.find_element(
                    By.CSS_SELECTOR,
                    ".prod_pricelist > ul > li:first-child > p > a strong"
                ).text
                price = price.replace(',', '')

                # 만약 크롤링한 데이터에도 있고 DB에도 있는 데이터라면
                try:
                    existing_board = Board.objects.get(name=parsed_name)

                    # 가격이 같은지 비교해서 다를 때만 db업데이트
                    if price != existing_board.price:
                        existing_board.price = price

                    # 가격 추적 모델도 업데이트
                    price_history = PriceHistory(
                        type="board",
                        part_id=existing_board.id,
                        start_date=timezone.now(),
                        price=price
                    )
                    price_history.save()

                # 그렇지 않고 크롤링한 데이터에는 있고 db에 없는 데이터라면
                except Board.DoesNotExist:
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
                    for item in spec_items:
                        if "소켓" in item:
                            socket_info = item.split("소켓")[1].strip().rstrip(')').strip()

                        elif "AMD" in item:
                            # am5
                            if item.split()[1] == "X670E":
                                grade = "X670E"

                            elif item.split()[1] == "X670":
                                grade = "X670"

                            elif item.split()[1] == "B650E":
                                grade = "B650E"

                            elif item.split()[1] == "B650":
                                grade = "B650"

                            elif item.split()[1] == "A620":
                                grade = "A620"

                            # am4
                            elif item.split()[1] == "A320":
                                grade = "A320"

                            elif item.split()[1] == "B350":
                                grade = "B350"

                            elif item.split()[1] == "B450":
                                grade = "B450"

                            elif item.split()[1] == "X370":
                                grade = "X370"

                            elif item.split()[1] == "X470":
                                grade = "X470"

                            elif item.split()[1] == "X450":
                                grade = "X450"

                            elif item.split()[1] == "X570":
                                grade = "X570"

                            elif item.split()[1] == "B550":
                                grade = "B550"

                            elif item.split()[1] == "A520":
                                grade = "A520"

                        elif "인텔" in item:
                            # 12세대
                            if item.split()[1] == "H610":
                                grade = "H610"

                            elif item.split()[1] == "B660":
                                grade = "B660"

                            elif item.split()[1] == "H670":
                                grade = "H670"

                            elif item.split()[1] == "Z690":
                                grade = "Z690"

                            # 13세대
                            elif item.split()[1] == "B760":
                                grade = "B760"

                            elif item.split()[1] == "H770":
                                grade = "H770"

                            elif item.split()[1] == "Z790":
                                grade = "Z790"

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

                    # 상세 링크 저장
                    link = board.find_element(
                        By.CSS_SELECTOR, ".prod_name a").get_attribute("href")

                    # 디테일 페이지로 이동
                    detail_page = webdriver.Chrome(service=service)
                    detail_page.get(link)
                    detail_page.implicitly_wait(30)

                    # 이미지 클릭
                    detail_page.find_element(
                        By.CSS_SELECTOR, ".detail_summary .thumb_w a"
                    ).click()

                    # image_source에 저장
                    image_source = detail_page.find_element(
                        By.CSS_SELECTOR,
                        "#imgExtensionArea #imgExtensionViewArea img"
                    ).get_attribute("src")

                    detail_page.close()

                    # 이미지 파일 경로 설정
                    image_path = os.path.join('C:/Users/ickyu/Desktop/pcgg_data/crawlers/board_img', parsed_name)

                    # 이미지를 파일로 저장
                    response = requests.get(image_source)
                    with open(image_path, "wb") as image_file:
                        image_file.write(response.content)

                    # s3에 업로드
                    file_url = upload_s3(image_path, parsed_name, "board")

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
                        changed_date=timezone.now(),
                        extinct=False
                    )
                    board_info.save()

                    # 가격 추적 모델도 업데이트
                    price_history = PriceHistory(
                        type="board",
                        part_id=board_info.id,
                        start_date=timezone.now(),
                        price=price
                    )
                    price_history.save()

            # 스크롤 끝까지 내리고
            driver.execute_script(
                "window.scrollTo(0, document.body.scrollHeight);"
            )
            time.sleep(2)

            # 다음 페이지로 이동
            success, current_page = move_to_next_page(driver, current_page)
            if not success:
                break

        # 이제 크롤링한 board 목록 전체가 구해졌음
        for crawled_board in crawled_boards:
            # 만약 크롤링한 board가 existing_boards에 있으면 목록에서 지워줌
            if crawled_board in existing_boards:
                existing_boards.remove(crawled_board)

        # 이제 목록에는 크롤링한 데이터에는 없고 DB에만 있는, 즉 단종 목록만 있음
        for extinct_board_name in existing_boards:
            extinct_board = Board.objects.get(name=extinct_board_name)
            extinct_board.extinct = True
            extinct_board.save()

        driver.quit()

    def cpu():
        service = ChromeService(
            "C:/Users/ickyu/Downloads/chromedriver-win64/chromedriver-win64/chromedriver.exe")
        driver = webdriver.Chrome(service=service)
        driver.get("https://prod.danawa.com/list/?cate=112747&15main_11_02")
        driver.implicitly_wait(10)

        # 코드 네임 전체 보기 클릭
        driver.find_element(
            By.CSS_SELECTOR, ".spec_opt_view > button:first-child").click()

        # CPU 코드 네임 필터 설정
        filter_options = ["807919", "748297", "801673", "706786", "740914"]
        for option in filter_options:
            driver.find_element(
                By.CSS_SELECTOR, f"input#searchAttributeValueRep{option}"
            ).click()

        # 르누아르는 css 선택자가 좀 다름
        other_options = ["772063", "676055"]
        for option in other_options:
            driver.find_element(
                By.CSS_SELECTOR, f"#searchAttributeValue{option}"
            ).click()

        # 잠시대기
        time.sleep(2)

        # 현재 페이지 번호 저장
        current_page = int(driver.find_element(
            By.CSS_SELECTOR, ".num.now_on").text)

        # DB에 있는 cpu 리스트
        existing_cpus = set(Cpu.objects.values_list('name', flat=True))

        # 크롤링한 cpu 목록
        crawled_cpus = []

        # cpu list 추출 및 개별 cpu정보 추출
        while True:
            # cpu list 추출
            cpu_list = driver.find_elements(
                By.CSS_SELECTOR,
                ".main_prodlist.main_prodlist_list > .product_list .prod_item.prod_layer:not(.product-pot)"
            )

            # 개별 cpu 정보 추출
            for cpu in cpu_list:

                # 이름
                name = cpu.find_element(By.CSS_SELECTOR, ".prod_name a").text
                parsed_names = name.split()
                parsed_name = ''
                for parsed_data in parsed_names:
                    # AMD나 INTEL로 시작하지 않고
                    if not parsed_data[0].startswith(("AMD", "INTEL")):
                        # 숫자로 시작하거나 영어로 시작하는 데이터
                        if parsed_data[0].isdigit() or parsed_data[0].isalpha():
                            parsed_name = parsed_data

                # 가격
                price = cpu.find_element(
                    By.CSS_SELECTOR,
                    ".prod_pricelist > ul > li:first-child > p > a strong"
                ).text
                price = price.replace(',', '')

                # 만약 크롤링한 데이터에도 있고 DB에도 있는 데이터라면
                try:
                    existing_cpu = Cpu.objects.get(name=parsed_name)

                    # 가격이 같은지 비교해서 다를 때만 db업데이트
                    if price != existing_cpu.price:
                        existing_cpu.price = price

                    # 가격 추적 모델도 업데이트
                    price_history = PriceHistory(
                        type="cpu",
                        part_id=existing_cpu.id,
                        start_date=timezone.now(),
                        price=price
                    )
                    price_history.save()

                # 그렇지 않고 크롤링한 데이터에는 있고 db에 없는 데이터라면
                except:
                    # /로 구분된 spec_list 추출
                    spec_list = cpu.find_element(By.CSS_SELECTOR, ".spec_list").text

                    # spec_list 재가공
                    spec_items = spec_list.split(" / ")

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

                        if "미탑재" not in item:
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

                    # 상세 링크 저장
                    link = cpu.find_element(
                        By.CSS_SELECTOR, ".prod_name a").get_attribute("href")

                    # 디테일 페이지로 이동
                    detail_page = webdriver.Chrome(service=service)
                    detail_page.get(link)
                    detail_page.implicitly_wait(30)

                    # 이미지 클릭
                    detail_page.find_element(
                        By.CSS_SELECTOR, ".detail_summary .thumb_w a"
                    ).click()

                    # image_source에 저장
                    image_source = detail_page.find_element(
                        By.CSS_SELECTOR,
                        "#imgExtensionArea #imgExtensionViewArea img"
                    ).get_attribute("src")

                    detail_page.close()

                    # 이미지 파일 경로 설정
                    image_path = os.path.join('C:/Users/ickyu/Desktop/pcgg_data/crawlers/imgs', parsed_name)

                    # 이미지를 파일로 저장
                    response = requests.get(image_source)
                    with open(image_path, "wb") as image_file:
                        image_file.write(response.content)

                    # s3에 업로드
                    file_url = upload_s3(image_path, parsed_name, "cpu")

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
                        changed_date=timezone.now(),
                        extinct=False
                    )
                    cpu_info.save()

                    # 가격 추적 모델도 업데이트
                    price_history = PriceHistory(
                        type="cpu",
                        part_id=cpu_info.id,
                        start_date=timezone.now(),
                        price=price
                    )
                    price_history.save()

            # 스크롤 끝까지 내리고
            driver.execute_script(
                "window.scrollTo(0, document.body.scrollHeight);"
            )
            time.sleep(2)

            # 다음 페이지로 이동
            success, current_page = move_to_next_page(driver, current_page)
            if not success:
                break

        # 이제 크롤링한 cpu 목록 전체가 구해졌음
        for crawled_cpu in crawled_cpus:
            # 만약 크롤링한 cpu가 existing_cpus에 있으면 목록에서 지워줌
            if crawled_cpu in existing_cpus:
                existing_cpus.remove(crawled_cpu)

        # 이제 목록에는 크롤링한 데이터에는 없고 DB에만 있는, 즉 단종 목록만 있음
        for extinct_cpu_name in existing_cpus:
            extinct_cpu = Cpu.objects.get(name=extinct_cpu_name)
            extinct_cpu.extinct = True
            extinct_cpu.save()

        driver.quit()

    tasks = []
    task1 = threading.Thread(target=board)
    task2 = threading.Thread(target=cpu)

    task1.start()
    tasks.append(task1)

    task2.start()
    tasks.append(task2)

    for task in tasks:
        task.join()