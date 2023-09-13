import sys
sys.path.append('C:/Users/SSAFY/Desktop/pcgg_data')

import os
os.environ.setdefault("DJANGO_SETTINGS_MODULE", "PCGG_data.settings")

import django
django.setup()

from crawlers.models import Cpu, PriceHistory
import boto3
import requests
from decouple import config
import re
import time
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.service import Service as ChromeService
from django.utils import timezone


def move_to_next_page(driver, current_page):
    try:
        # 다음 페이지가 존재할 때
        if driver.find_element(
                By.CSS_SELECTOR,
                f"a.num[onclick*='movePage({current_page + 1})']"
        ):

            # 현재 10의 배수 페이지면 화살표 눌러야함
            if current_page % 10 == 0:
                driver.find_element(
                    By.CSS_SELECTOR, "a.edge_nav nav_next").click()
                time.sleep(2)  # 페이지 로딩 대기

            else:
                driver.find_element(
                    By.CSS_SELECTOR,
                    f"a.num[onclick*='movePage({current_page + 1})']"
                ).click()

                time.sleep(2)  # 페이지 로딩 대기

            # 현재 페이지 번호 갱신
            current_page = int(driver.find_element(
                By.CSS_SELECTOR, ".num.now_on").text)

    except Exception as e:
        print(e)
        return False, current_page  # 페이지 이동 실패 시 False와 현재 페이지 반환

    return True, current_page  # 페이지 이동 성공 시 True와 현재 페이지 반환


def get_cpu_list():
    service = ChromeService(
        "C:/Users/SSAFY/Downloads/chromedriver-win64/chromedriver-win64/chromedriver.exe")
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
            except Cpu.DoesNotExist:
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
                image_path = os.path.join('cpu_img', parsed_name)

                # 이미지를 파일로 저장
                response = requests.get(image_source)
                with open(image_path, "wb") as image_file:
                    image_file.write(response.content)

                # AWS 자격 증명 설정
                aws_access_key_id = config('S3_ACCESS_KEY_ID')
                aws_secret_access_key = config('S3_SECRET_ACCESS_KEY')

                # S3 클라이언트 생성
                s3 = boto3.client('s3',
                                  aws_access_key_id=aws_access_key_id,
                                  aws_secret_access_key=aws_secret_access_key
                                  )

                # 업로드할 이미지 파일명과 S3 버킷 이름 설정
                image_file_name = image_path
                bucket_name = 'pcgg'
                object_key = f'cpu/{parsed_name}'  # 객체 키는 이미지 파일명과 동일하게 설정

                # 이미지 업로드
                s3.upload_file(
                    image_file_name,
                    bucket_name,
                    object_key,
                    ExtraArgs={
                        'ACL': 'public-read',
                        'ContentType': 'image/jpeg'  # content-type을 'image/jpeg'로 지정
                    }
                )

                # 이미지 URL을 'Content-Disposition' 메타데이터를 포함한 형식으로 업데이트
                file_url = f"https://{bucket_name}.s3.ap-northeast-2.amazonaws.com/{object_key}"

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


get_cpu_list()
