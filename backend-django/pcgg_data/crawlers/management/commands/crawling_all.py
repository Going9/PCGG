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
        # tasks = []
        # cpu_url = "https://prod.danawa.com/list/?cate=112747"
        # board_url = "https://prod.danawa.com/list/?cate=112751&15main_11_02"
        #
        # task1 = threading.Thread(target=get_cpu_list, args=(cpu_url,))
        # task2 = threading.Thread(target=get_board_list, args=(board_url,))
        # task3 = threading.Thread(target=get_cooler_list, args=("https://prod.danawa.com/list/?cate=11236855",))
        # task4 = threading.Thread(target=get_ram_list, args=("https://prod.danawa.com/list/?cate=112752",))
        # task5 = threading.Thread(target=get_ssd_list, args=("https://prod.danawa.com/list/?cate=112760",))
        # task6 = threading.Thread(target=get_gpu_list, args=("https://prod.danawa.com/list/?cate=112753",))
        # task7 = threading.Thread(target=get_case_list, args=("https://prod.danawa.com/list/?cate=112775",))
        # task8 = threading.Thread(target=get_power_list, args=("https://prod.danawa.com/list/?cate=112777",))
        #
        # task1.start()
        # tasks.append(task1)
        #
        # task2.start()
        # tasks.append(task2)
        #
        # task3.start()
        # tasks.append(task3)
        #
        # task4.start()
        # tasks.append(task4)
        #
        # task5.start()
        # tasks.append(task5)
        #
        # task6.start()
        # tasks.append(task6)
        #
        # task7.start()
        # tasks.append(task7)
        #
        # task8.start()
        # tasks.append(task8)
        #
        # for task in tasks:
        #     task.join()

        get_ssd_list("https://prod.danawa.com/list/?cate=112760")
        get_gpu_list("https://prod.danawa.com/list/?cate=112753")
        get_case_list("https://prod.danawa.com/list/?cate=112775")
        get_power_list("https://prod.danawa.com/list/?cate=112777")
        get_cooler_list("https://prod.danawa.com/list/?cate=11236855")
        get_ram_list("https://prod.danawa.com/list/?cate=112752")
        get_cpu_list("https://prod.danawa.com/list/?cate=112747")
        get_board_list("https://prod.danawa.com/list/?cate=112751&15main_11_02")

        # tasks = []
        # cpu_url = "https://prod.danawa.com/list/?cate=112747"
        # board_url = "https://prod.danawa.com/list/?cate=112751&15main_11_02"
        #
        # task1 = Process(target=get_cpu_list, args=(cpu_url,))
        # task2 = Process(target=get_board_list, args=(board_url,))
        # task3 = Process(target=get_cooler_list, args=("https://prod.danawa.com/list/?cate=11236855",))
        # task4 = Process(target=get_ram_list, args=("https://prod.danawa.com/list/?cate=112752",))
        # task5 = Process(target=get_ssd_list, args=("https://prod.danawa.com/list/?cate=112760",))
        # task6 = Process(target=get_gpu_list, args=("https://prod.danawa.com/list/?cate=112753",))
        # task7 = Process(target=get_case_list, args=("https://prod.danawa.com/list/?cate=112775",))
        # task8 = Process(target=get_power_list, args=("https://prod.danawa.com/list/?cate=112777",))
        #
        # task1.start()
        # tasks.append(task1)
        #
        # task2.start()
        # tasks.append(task2)
        #
        # task3.start()
        # tasks.append(task3)
        #
        # task4.start()
        # tasks.append(task4)
        #
        # task5.start()
        # tasks.append(task5)
        #
        # task6.start()
        # tasks.append(task6)
        #
        # task7.start()
        # tasks.append(task7)
        #
        # task8.start()
        # tasks.append(task8)
        #
        # for task in tasks:
        #     task.join()


