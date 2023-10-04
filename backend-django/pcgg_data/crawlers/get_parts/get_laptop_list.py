import sys

sys.path.append('/')

import os

os.environ.setdefault("DJANGO_SETTINGS_MODULE", "PCGG_data.settings")

import django

django.setup()

import re
import time
from crawlers.models import Laptop, PriceHistory
from selenium.webdriver.common.by import By
from django.utils import timezone
from crawlers.get_parts.tools.tools import get_driver, get_product_list, save_current_page, move_to_next_page, \
    upload_to_storage, update_history, extract_product_info, update_database, get_name_and_price


def get_laptop_list(url: str):
    global laptop_info
    print("laptop 크롤링 시작")

    driver = get_driver(url)

    # 옵션 전체보기 클릭
    driver.find_element(
        By.CSS_SELECTOR, "#frmProductList > div.option_nav > div.nav_header > div.head_opt > button"
    ).click()

    # cpu 코드명 클릭
    driver.find_element(
        By.CSS_SELECTOR,
        "#extendSearchOptionpriceCompare > div > dl:nth-child(10) > dd > div > button.btn_spec_view.btn_view_more"
    ).click()

    # 타이거레이크, 르누아르 이후 노트북만 필터
    # 드래곤레인지, 피닉스, 랩터레이크, 램브란트-R, 램브란트, 엘더레이크, 엘더레이크-N, 바르셀로, 바르셀로-R,
    attribute_value = ["823528", "823516", "823300", "823525", "758011", "758008", "845353", "762679", "823522"]
    for option in attribute_value:
        driver.find_element(
            By.CSS_SELECTOR, f"#searchAttributeValue{option}"
        ).click()

    # 잠시대기
    time.sleep(2)

    # 현재 페이지 번호 저장
    current_page = save_current_page(driver)

    # DB에 있는 laptop 리스트
    existing_laptops = set(Laptop.objects.values_list('name', flat=True))

    # 크롤링한 laptop 목록
    crawled_laptops = []

    # 해상도 종류
    resolution_list = {
        "1280x720": "1280x720",
        "1280x800": "1280x800",
        "1366x768": "1366x768",
        "1366x912": "1366x912",
        "1440x900": "1440x900",
        "1536x1024": "1536x1024",
        "1600x900": "1600x900",
        "1800x1200": "1800x1200",
        "1920x1200": "1920x1200",
        "1920x1080": "1920x1080",
        "1920x1280": "1920x1280",
        "2000x1200": "2000x1200",
        "2048x1536": "2048x1536",
        "2160x1350": "2160x1350",
        "2160x1440": "2160x1440",
        "2240x1400": "2240x1400",
        "2256x1504": "2256x1504",
        "2400x1600": "2400x1600",
        "2496x1664": "2496x1664",
        "2560x1600": "2560x1600",
        "2560x1440": "2560x1440",
        "2560x1664": "2560x1664",
        "2560x1920": "2560x1920",
        "2736x1824": "2736x1824",
        "2880x1620": "2880x1620",
        "2880x1800": "2880x1800",
        "2880x1864": "2880x1864",
        "2880x1920": "2880x1920",
        "3000x2000": "3000x2000",
        "3024x1964": "3024x1964",
        "3072x1440": "3072x1440",
        "3072x1920": "3072x1920",
        "3200x2000": "3200x2000",
        "3240x2160": "3240x2160",
        "3456x2160": "3456x2160",
        "3456x2234": "3456x2234",
        "3840x2160": "3840x2160",
        "3840x2400": "3840x2400"
    }

    # cpu 종류
    cpu_list = {
        "7945HX": "7945HX",
        "7845HX": "7845HX",
        "7745HX": "7745HX",
        "7940HS": "7940HS",
        "7840HS": "7840HS",
        "7640HS": "7640HS",
        "7840U": "7840U",
        "7540U": "7540U",
        "i9-13980HX": "i9-13980HX",
        "i9-13950HX": "i9-13950HX",
        "i9-13900HX": "i9-13900HX",
        "i7-13850HX": "i7-13850HX",
        "i7-13700HX": "i7-13700HX",
        "i7-13650HX": "i7-13650HX",
        "i5-13500HX": "i5-13500HX",
        "i9-13900HK": "i9-13900HK",
        "i9-13905H": "i9-13905H",
        "i9-13900H": "i9-13900H",
        "i7-13800H": "i7-13800H",
        "i7-13700H": "i7-13700H",
        "i7-13620H": "i7-13620H",
        "i5-13500H": "i5-13500H",
        "i5-13420H": "i5-13420H",
        "i7-1370P": "i7-1370P",
        "i7-1360P": "i7-1360P",
        "i5-1350P": "i5-1350P",
        "i5-1340P": "i5-1340P",
        "i7-1365U": "i7-1365U",
        "i7-1355U": "i7-1355U",
        "i5-1345U": "i5-1345U",
        "i5-1335U": "i5-1335U",
        "i3-1315U": "i3-1315U",
        "U300": "U300",
        "i3-N305": "i3-N305",
        "N200": "N200",
        "N100": "N100",
        "7735HS": "7735HS",
        "7535HS": "7535HS",
        "7735U": "7735U",
        "7535U": "7535U",
        "7730U": "7730U",
        "7530U": "7530U",
        "7330U": "7330U",
        "7520U": "7520U",
        "7320U": "7320U",
        "i9-12950HX": "i9-12950HX",
        "i9-12900HX": "i9-12900HX",
        "i7-12850HX": "i7-12850HX",
        "i7-12800HX": "i7-12800HX",
        "i5-12600HX": "i5-12600HX",
        "i9-12900HK": "i9-12900HK",
        "i9-12900H": "i9-12900H",
        "i7-12800H": "i7-12800H",
        "i7-12700H": "i7-12700H",
        "i7-12650H": "i7-12650H",
        "i5-12500H": "i5-12500H",
        "i5-12450H": "i5-12450H",
        "i7-1280P": "i7-1280P",
        "i7-1270P": "i7-1270P",
        "i7-1260P": "i7-1260P",
        "i5-1240P": "i5-1240P",
        "i3-1220P": "i3-1220P",
        "i7-1265U": "i7-1265U",
        "i7-1250U": "i7-1250U",
        "i7-1255U": "i7-1255U",
        "i5-1245U": "i5-1245U",
        "i5-1230U": "i5-1230U",
        "i5-1235U": "i5-1235U",
        "i3-1215U": "i3-1215U",
        "8505": "8505",
        "6950H": "6950H",
        "6850H": "6850H",
        "6850HS": "6850HS",
        "6850U": "6850U",
        "6650H": "6650H",
        "6650U": "6650U",
        "6900HX": "6900HX",
        "6900HS": "6900HS",
        "6800H": "6800H",
        "6800HS": "6800HS",
        "6800U": "6800U",
        "6600H": "6600H",
        "6600HS": "6600HS",
        "6600U": "6600U",
        "5825U": "5825U",
        "5875U": "5875U",
        "5625U": "5625U",
        "5675U": "5675U",
        "5475U": "5475U",
        "5425U": "5425U",
        "5560U": "5560U"
    }

    # gpu 종류
    gpu_list = {
        "RTX4090": "RTX4090",
        "RTX4080": "RTX4080",
        "RTX4070": "RTX4070",
        "RTX4060": "RTX4060",
        "RTX4050": "RTX4050",
        "RTX3080 Ti": "RTX3080 Ti",
        "RTX3080": "RTX3080",
        "RTX3070 Ti": "RTX3070 Ti",
        "RTX3070": "RTX3070",
        "RTX3060": "RTX3060",
        "RTX3050 Ti": "RTX3050 Ti",
        "RTX3050": "RTX3050",
        "RTX2080 SUPER": "RTX2080 SUPER",
        "RTX2080": "RTX2080",
        "RTX2070 SUPER": "RTX2070 SUPER",
        "RTX2070": "RTX2070",
        "RTX2060": "RTX2060",
        "RTX2050": "RTX2050",
        "GTX1660 Ti": "GTX1660 Ti",
        "GTX1650 Ti": "GTX1650 Ti",
        "GTX1650": "GTX1650",
        "MX570 A": "MX570 A",
        "MX570": "MX570",
        "MX550": "MX550",
        "MX450": "MX450",
        "MX350": "MX350",
        "MX330": "MX330",
        "MX250": "MX250",
        "MX230": "MX230",
        "MX150": "MX150",
        "MX130": "MX130",
        "GTX1060": "GTX1060",
        "GTX1050 Ti": "GTX1050 Ti",
        "GTX1050": "GTX1050",
        "라데온 RX 7600M XT": "라데온 RX 7600M XT",
        "라데온 RX 7600S": "라데온 RX 7600S",
        "라데온 RX 6800S": "라데온 RX 6800S",
        "라데온 RX 6700S": "라데온 RX 6700S",
        "라데온 RX 6850M XT": "라데온 RX 6850M XT",
        "라데온 RX 6700M": "라데온 RX 6700M",
        "라데온 RX 6650M": "라데온 RX 6650M",
        "라데온 RX 6600M": "라데온 RX 6600M",
        "라데온 RX 6500M": "라데온 RX 6500M",
        "라데온 RX 5500M": "라데온 RX 5500M",
        "Iris Xe MAX": "Iris Xe MAX",
        "Iris Xe": "Iris Xe",
        "Iris Plus": "Iris Plus",
        "UHD Graphics": "UHD Graphics",
        "UHD 630": "UHD 630",
        "UHD 620": "UHD 620",
        "UHD 615": "UHD 615",
        "UHD 617": "UHD 617",
        "UHD 610": "UHD 610",
        "UHD 605": "UHD 605",
        "UHD 600": "UHD 600",
        "Iris Plus 645": "Iris Plus 645",
        "Iris Plus 655": "Iris Plus 655",
        "Iris Plus 650": "Iris Plus 650",
        "Iris Plus 640": "Iris Plus 640",
        "Iris Pro 5200": "Iris Pro 5200",
        "Iris 550": "Iris 550",
        "Iris 540": "Iris 540",
        "HD 620": "HD 620",
        "HD 615": "HD 615",
        "HD 610": "HD 610",
        "HD 520": "HD 520",
        "HD 515": "HD 515",
        "HD 500": "HD 500",
        "HD 6000": "HD 6000",
        "HD 5500": "HD 5500",
        "HD 5000": "HD 5000",
        "HD 4400": "HD 4400",
        "HD 4200": "HD 4200",
        "HD 4000": "HD 4000",
        "HD 3000": "HD 3000",
        "HD Graphics": "HD Graphics",
        "Radeon 780M": "Radeon 780M",
        "Radeon 760M": "Radeon 760M",
        "Radeon 740M": "Radeon 740M",
        "Radeon 680M": "Radeon 680M",
        "Radeon 660M": "Radeon 660M",
        "Radeon 610M": "Radeon 610M",
        "Radeon Graphics": "Radeon Graphics"
    }

    # laptop list 추출 및 개별 laptop정보 추출
    while True:
        # laptop list 추출
        product_list = get_product_list(driver)

        # 개별 laptop 정보 추출
        for laptop in product_list:

            # 파싱 전 이름, 가격, 디테일 페이지 추출
            name, price, detail_page = get_name_and_price(laptop)

            # 제조사 추출
            try:
                manufacturer = name.split()[0]

            except Exception as e:
                print(e)
                print(name)
                manufacturer = "unknown"

            # 이름 파싱
            # 파일명으로 사용할 수 없는 문자를 언더스코어로 대체
            invalid_characters = r'[\/:*?"<>|]'
            parsed_name = re.sub(invalid_characters, '_', name)
            parsed_name = parsed_name.replace(" ", "_")

            # 업로드한 이미지 주소
            file_url = upload_to_storage(detail_page, "laptop", parsed_name)

            # 만약 크롤링한 데이터에도 있고 DB에도 있는 데이터라면
            try:
                update_history(Laptop, parsed_name, price, "laptop")

            # 그렇지 않고 크롤링한 데이터에는 있고 db에 없는 데이터라면
            except:
                spec_items = extract_product_info(laptop)

                # 모델 저장에 필요한 변수들
                type = ""
                os = ""
                screen_size = float()
                refresh_rate = 60
                resolution = ""
                brightness = 0
                cpu = ""
                ram_capacity = 0
                ram_clock = 0
                ram_upgradable = False
                gpu = ""
                tgp = 0
                ssd = ""
                cellular = False
                hdmi = False
                thunderbolt = False
                usb_a = False
                usb_c = False
                sd_card = ""
                weight = float()
                battery = 0

                # 데이터 파싱해서 변수에 저장
                for index, item in enumerate(spec_items):
                    try:
                        if item in ["태블릿PC", "노트북", "2 in 1"]:
                            type = item

                        if "운영체제" in item:
                            os = item.split()[1]

                        elif "화면정보" in item:
                            # 정규 표현식을 사용하여 숫자를 추출
                            pattern = r'(\d+\.\d+|\d+)(?=\s*인치)'  # 정수 또는 소수점을 포함한 숫자 패턴
                            screen_size = float(re.findall(pattern, item)[0])

                        elif item.split("(")[0] in resolution_list:
                            resolution = resolution_list.get(item.split("(")[0], None)

                        elif "주사율" in item:
                            refresh_rate = int(item.split()[1].replace("Hz", ""))

                        elif "nit" in item:
                            brightness = item.replace("nit", "")

                        elif item.split()[0] in cpu_list:
                            cpu = cpu_list.get(item.split()[0], None)

                        elif "램 용량" in item:
                            ram_capacity = int(item.split()[2].replace("GB", ""))

                        elif "램 교체" in item:
                            if "가능" in item:
                                ram_upgradable = True

                            else:
                                ram_upgradable = False

                        elif item in gpu_list:
                            gpu = gpu_list.get(item, None)

                        elif "TGP" in item:
                            tgp = int(item.split()[1].replace("W", ""))

                        elif "저장장치" in item:
                            capacity = int(re.search(r'\d+', spec_items[index + 1]).group())
                            if "GB" in spec_items[index + 1]:
                                ssd = capacity / 1000

                            elif "TB" in spec_items[index + 1]:
                                ssd = capacity

                        elif "셀룰러" in item:
                            cellular = True

                        elif "HDMI" in item:
                            hdmi = True

                        elif "썬더볼트" in item and "USB-C" in item:
                            thunderbolt = True
                            usb_c = True

                        elif "USB-C" in item:
                            usb_c = True

                        elif "USB-A" in item:
                            usb_a = True

                        elif "SD카드" in item:
                            if "MicroSD카드" in item:
                                sd_card = "MicroSD"

                            else:
                                sd_card = "SD"

                        elif "무게" in item:
                            if "kg" in item:
                                weight = float(item.split()[1].replace("kg", ""))

                            elif "g" in item and "kg" not in item:
                                weight = float(item.split()[1].replace("g", "")) / 1000

                        elif "배터리" in item:
                            battery = float(item.split()[2].replace("Wh", ""))

                    except Exception as e:
                        print(e, "\n")
                        print("오류: ", item, "\n")

                    # print("이름", parsed_name, "\n")
                    # print("가격", price, "\n")
                    # print("이미지 주소", file_url, "\n")
                    # print("제조사", manufacturer, "\n")
                    # print("타입", type, "\n")
                    # print("운영체제", os, "\n")
                    # print("화면 크기", screen_size, "\n")
                    # print("주사율", refresh_rate, "\n")
                    # print("해상도", resolution, "\n")
                    # print("밝기", brightness, "\n")
                    # print("cpu", cpu, "\n")
                    # print("램 용량", ram_capacity, "\n")
                    # print("램 교체 가능 여부", ram_upgradable, "\n")
                    # print("gpu", gpu, "\n")
                    # print("tgp", tgp, "\n")
                    # print("ssd", ssd, "\n")
                    # print("셀룰러", cellular, "\n")
                    # print("hdmi", hdmi, "\n")
                    # print("썬더볼트", thunderbolt, "\n")
                    # print("usb_a", usb_a, "\n")
                    # print("usb_c", usb_c, "\n")
                    # print("sd_card", sd_card, "\n")
                    # print("무게", weight, "\n")
                    # print("배터리", battery, "\n")
                    #
                    #
                    #
                    # breakpoint()

                    # laptop 모델 업데이트

                try:
                    laptop_info = Laptop(
                        name=parsed_name,
                        price=price,
                        image_source=file_url,
                        extinct=False,
                        manufacturer=manufacturer,
                        type=type,
                        os=os,
                        screen_size=screen_size,
                        refresh_rate=refresh_rate,
                        resolution=resolution,
                        brightness=brightness,
                        cpu=cpu,
                        ram_capacity=ram_capacity,
                        ram_upgradeable=ram_upgradable,
                        gpu=gpu,
                        tgp=tgp,
                        ssd=ssd,
                        cellular=cellular,
                        hdmi=hdmi,
                        thunderbolt=thunderbolt,
                        usb_a=usb_a,
                        usb_c=usb_c,
                        sd_card=sd_card,
                        weight=weight,
                        battery=battery
                    )
                    laptop_info.save()

                except Exception as e:
                    print(e, "\n")

                try:
                    # 가격 추적 모델도 업데이트
                    price_history = PriceHistory(
                        type="laptop",
                        part_id=laptop_info.id,
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
        update_database(crawled_laptops, existing_laptops, Laptop)

    except Exception as e:
        print(e, "\n")

    print("laptop crawling 끝")
    driver.quit()


get_laptop_list("https://prod.danawa.com/list/?cate=112758&15main_11_02")
