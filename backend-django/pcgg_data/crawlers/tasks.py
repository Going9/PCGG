# tasks.py (크롤링을 처리하는 Django 앱 내에서)

from celery import Celery
from crawlers.get_parts.get_cpu_list import get_cpu_list
from crawlers.get_parts.get_board_list import get_board_list
from crawlers.get_parts.get_cooler_list import get_cooler_list
from crawlers.get_parts.get_ram_list import get_ram_list
from crawlers.get_parts.get_ssd_list import get_ssd_list
from crawlers.get_parts.get_gpu_list import get_gpu_list
from crawlers.get_parts.get_case_list import get_case_list
from crawlers.get_parts.get_power_list import get_power_list
from crawlers.get_parts.get_laptop_list import get_laptop_list
from collectors.get_parts.get_peripheral_list import main

BROKER_URL = 'redis://localhost:6379/0'
CELERY_RESULT_BACKEND = 'redis://localhost:6379/0'

app = Celery('tasks', broker=BROKER_URL, backend=CELERY_RESULT_BACKEND)


@app.task
def crawling_task():
    # 크롤링 작업을 여기에 구현
    funcs = {
        get_gpu_list: "https://prod.danawa.com/list/?cate=112753",
        get_ssd_list: "https://prod.danawa.com/list/?cate=112760",
        get_case_list: "https://prod.danawa.com/list/?cate=112775",
        get_power_list: "https://prod.danawa.com/list/?cate=112777",
        get_cooler_list: "https://prod.danawa.com/list/?cate=11236855",
        get_ram_list: "https://prod.danawa.com/list/?cate=112752",
        get_cpu_list: "https://prod.danawa.com/list/?cate=112747",
        get_board_list: "https://prod.danawa.com/list/?cate=112751&15main_11_02",
        get_laptop_list: "https://prod.danawa.com/list/?cate=112758&15main_11_02",
    }
    main()
    for func, url in funcs.items():
        try:
            func(url)
        except Exception as e:
            print(e)
            continue
