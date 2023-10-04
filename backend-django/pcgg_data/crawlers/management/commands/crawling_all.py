import threading
from multiprocessing import Process
from django.core.management.base import BaseCommand
from crawlers.get_parts.get_cpu_list import get_cpu_list
from crawlers.get_parts.get_board_list import get_board_list
from crawlers.get_parts.get_cooler_list import get_cooler_list
from crawlers.get_parts.get_ram_list import get_ram_list
from crawlers.get_parts.get_ssd_list import get_ssd_list
from crawlers.get_parts.get_gpu_list import get_gpu_list
from crawlers.get_parts.get_case_list import get_case_list
from crawlers.get_parts.get_power_list import get_power_list


class Command(BaseCommand):
    help = 'pcgg 크롤링 명령어'
    output_transaction = True

    def handle(self, *args, **options):
        funcs = {
            get_gpu_list(): "https://prod.danawa.com/list/?cate=112753",
            get_ssd_list(): "https://prod.danawa.com/list/?cate=112760",
            get_case_list(): "https://prod.danawa.com/list/?cate=112775",
            get_power_list(): "https://prod.danawa.com/list/?cate=112777",
            get_cooler_list(): "https://prod.danawa.com/list/?cate=11236855",
            get_ram_list(): "https://prod.danawa.com/list/?cate=112752",
            get_cpu_list(): "https://prod.danawa.com/list/?cate=112747",
            get_board_list(): "https://prod.danawa.com/list/?cate=112751&15main_11_02"
        }

        for func, url in funcs.items():
            try:
                func(url)
            except Exception as e:
                print(e)
                continue


