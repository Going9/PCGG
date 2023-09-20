import threading
from django.core.management.base import BaseCommand
from crawlers.get_parts.get_cpu_list import get_cpu_list
from crawlers.get_parts.get_board_list import get_board_list


class Command(BaseCommand):
    help = 'pcgg 크롤링 명령어'
    output_transaction = True

    def handle(self, *args, **options):
        tasks = []
        task1 = threading.Thread(target=get_cpu_list)
        task2 = threading.Thread(target=get_board_list)

        task1.start()
        tasks.append(task1)

        task2.start()
        tasks.append(task2)

        for task in tasks:
            task.join()