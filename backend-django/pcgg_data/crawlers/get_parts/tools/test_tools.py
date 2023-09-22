import time
import requests
import boto3
import selenium_async

import crawlers


from botocore.exceptions import NoCredentialsError
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.chrome.service import Service as ChromeService
from decouple import config
from crawlers.models import PriceHistory
from django.utils import timezone


def get_driver(url: str, driver: selenium_async.WebDriver):
    driver.get(url)
    driver.implicitly_wait(10)

    return driver


def save_current_page(driver: selenium_async.WebDriver) -> int:
    current_page = int(driver.find_element(
        By.CSS_SELECTOR, ".num.now_on").text)

    return current_page


def get_product_list(driver: selenium_async.WebDriver):
    wait = WebDriverWait(driver, 10)
    product_list = wait.until(EC.presence_of_all_elements_located(
        (By.CSS_SELECTOR, ".main_prodlist.main_prodlist_list > .product_list .prod_item.prod_layer:not(.product-pot)")))

    return product_list


def move_to_next_page(driver: selenium_async.WebDriver, current_page):
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


def get_name_and_price(product, detail_page: selenium_async.WebDriver):
    # 상세 링크 저장
    link = product.find_element(
        By.CSS_SELECTOR, ".prod_name a"
    ).get_attribute("href")

    # 디테일 페이지로 이동
    detail_page.get(link)
    time.sleep(2)
    detail_page.implicitly_wait(30)

    if detail_page is None:
        print("디테일 페이지가 없습니다.")

    # 이름 저장
    name = detail_page.find_element(
        By.CSS_SELECTOR,
        "#blog_content > div.summary_info > div.top_summary > h3 > span"
    ).text

    # 가격 저장
    try:
        price = detail_page.find_element(
            By.CSS_SELECTOR,
            "#blog_content > div.summary_info > div.detail_summary > div.summary_left > div.lowest_area > div.lowest_top > div.row.lowest_price > span.lwst_prc > a > em"
        ).text
        price = int(price.replace(',', ''))

        return name, price, detail_page

    except Exception as e:
        print(e, "가격이 없습니다.")
        price = 0

        return name, price, detail_page


def upload_to_storage(detail_page, model_name: str, parsed_name):
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

    # AWS 자격 증명 설정
    access_key = config('S3_ACCESS_KEY_ID')
    secret_key = config('S3_SECRET_ACCESS_KEY')
    service_name = 's3'
    endpoint_url = 'https://kr.object.ncloudstorage.com'

    # S3 클라이언트 생성
    s3 = boto3.client(service_name,
                      endpoint_url=endpoint_url,
                      aws_access_key_id=access_key,
                      aws_secret_access_key=secret_key
                      )

    bucket_name = 'pcgg'
    object_key = f'{model_name}/{parsed_name}'

    # 이미지를 파일로 저장
    response = requests.get(image_source)

    # 파일을 바이트 스트림으로 읽고 S3 버킷에 업로드
    try:
        s3.put_object(
            Bucket=bucket_name,
            Key=f'{model_name}/{parsed_name}',
            Body=response.content,
            ACL='public-read',
            ContentType='image/jpeg'
        )
        file_url = f"https://kr.object.ncloudstorage.com/pcgg/{object_key}"

        return file_url

    except NoCredentialsError:
        print("AWS 자격 증명이 없습니다.")
        return None

    except Exception as e:
        print(f"파일 업로드 중 오류 발생: {e}")
        return None


def update_history(model: crawlers.models, parsed_name, price, model_name: str):
    existing_model = model.objects.get(name=parsed_name)

    # 가격이 같은지 비교해서 다를 때만 db업데이트
    if price != existing_model.price:
        existing_model.price = price

    # 가격 추적 모델도 업데이트
    price_history = PriceHistory(
        type=model_name,
        part_id=existing_model.id,
        start_date=timezone.now(),
        price=price
    )
    price_history.save()

    return


def extract_product_info(model):
    # /로 구분된 spec_list 추출
    spec_list = model.find_element(By.CSS_SELECTOR, ".spec_list").text

    # spec_list 재가공
    spec_items = spec_list.split(" /")
    spec_items = [item.strip() for item in spec_items]

    return spec_items


def update_database(crawled_models, existing_models, model: crawlers.models):
    # 이제 크롤링한 model 목록 전체가 구해졌음
    for crawled_model in crawled_models:
        # 만약 크롤링한 model이 existing_models에 있으면 목록에서 지워줌
        if crawled_model in existing_models:
            existing_models.remove(crawled_model)

    # 이제 목록에는 크롤링한 데이터에는 없고 DB에만 있는, 즉 단종 목록만 있음
    for extinct_model_name in existing_models:
        extinct_model = model.objects.get(name=extinct_model_name)
        extinct_model.extinct = True
        extinct_model.save()