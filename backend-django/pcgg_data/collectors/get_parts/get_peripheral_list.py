import sys
import time

sys.path.append('/')

import os

os.environ.setdefault("DJANGO_SETTINGS_MODULE", "PCGG_data.settings")

import django

django.setup()

import requests
import asyncio
from collectors.models import Keyboard, Printer, Monitor, Mouse
from decouple import config
from asgiref.sync import sync_to_async


@sync_to_async
def get_peripherals_list(peripheral_device: str, model):
    print(f"{peripheral_device} 콜렉팅 시작")

    # 네이버 api url
    url = f'https://openapi.naver.com/v1/search/shop.json?query={peripheral_device}&display=100'

    # 필요한 헤더 설정
    headers = {
        'X-Naver-Client-Id': config("naver_id"),
        'X-Naver-Client-Secret': config("naver_secret")
    }

    # GET 요청 보내기
    response = requests.get(url, headers=headers)

    # DB에 있는 laptop 리스트
    existing_data = set(model.objects.values_list('name', flat=True))

    for keyboard in response.json()['items']:
        name = keyboard['title'].replace('<b>', '').replace('</b>', '')
        if keyboard['lprice'] == '':
            lprice = 0

        else:
            lprice = int(keyboard['lprice'])

        if keyboard['hprice'] == '':
            hprice = 0

        else:
            hprice = int(keyboard['hprice'])

        image_source = keyboard['image']
        link = keyboard['link']
        brand = keyboard['brand']

        # db에 있는 데이터면 가격만 업데이트
        if name in existing_data:
            existing_model = model.objects.get(name=name)
            existing_model.lprice = lprice
            existing_model.hprice = hprice
            existing_model.save()

        # 그렇지 않고 새로운 데이터면 db에 저장
        else:
            try:
                model.objects.create(name=name, lprice=lprice, hprice=hprice, image_source=image_source, link=link,
                                     brand=brand)

            except Exception as e:
                print(keyboard, e, "\n")
                continue


async def main():
    a = time.time()
    peripherals = [Printer, Keyboard, Mouse, Monitor]
    tasks = [get_peripherals_list(peripheral.__name__, peripheral) for peripheral in peripherals]
    await asyncio.gather(*tasks)
    b = time.time()
    print(b - a)  # asyncio.gather로 비동기 함수 실행
    # 10.750402212142944

asyncio.run(main())


def not_async():
    a = time.time()
    peripherals = [Printer, Keyboard, Mouse, Monitor]
    for peripheral in peripherals:
        get_peripherals_list(peripheral.__name__, peripheral)
    b = time.time()
    print(b - a)
    #10.983697891235352


# not_async()
