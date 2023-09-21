import datetime
from django.utils import timezone
from django.db import models


# Create your models here.
class BasicInfo(models.Model):
    name = models.CharField(max_length=100, null=True)
    price = models.IntegerField(null=True)
    image_source = models.URLField(null=True)
    extinct = models.BooleanField(default=False)
    changed_date = models.DateTimeField(auto_now=True)

    def __str__(self):
        return self.name

    class Meta:
        abstract = True


class PriceHistory(models.Model):
    type = models.CharField(max_length=20, null=True)
    part_id = models.PositiveIntegerField(null=False)
    start_date = models.DateTimeField(auto_now=True)
    end_date = models.DateTimeField(
        default=datetime.datetime(9999, 12, 31, 23, 59, 59, tzinfo=timezone.utc))
    price = models.IntegerField(null=False)

    def __str__(self):
        return self.type

    class Meta:
        db_table = 'price_history'


class Cpu(BasicInfo):
    socket_info = models.CharField(max_length=20, null=True)
    ddr4 = models.BooleanField(default=False, null=True)
    ddr5 = models.BooleanField(default=False, null=True)
    integrated_graphics = models.BooleanField(default=False, null=True)
    cooler_included = models.BooleanField(default=False, null=True)
    single_score = models.IntegerField(null=True)
    multi_score = models.IntegerField(null=True)

    def __str__(self):
        return self.name

    class Meta:
        db_table = 'part_cpu'


class Board(BasicInfo):
    socket_info = models.CharField(max_length=20, null=True)
    grade = models.CharField(max_length=20, null=True)
    memory_spec = models.CharField(max_length=10, null=True)
    size = models.CharField(max_length=30, null=True)
    pcie3 = models.BooleanField(default=False, null=True)
    pcie4 = models.BooleanField(default=False, null=True)
    pcie5 = models.BooleanField(default=False, null=True)
    m2_count = models.IntegerField(null=True)

    def __str__(self):
        return self.name

    class Meta:
        db_table = 'part_mainboard'


class Ssd(BasicInfo):
    pcie_ver = models.IntegerField(null=True)
    reading_speed = models.IntegerField(null=True)
    writing_speed = models.IntegerField(null=True)
    capacity = models.FloatField(null=True)
    manufacturer = models.CharField(max_length=20, null=True)

    def __str__(self):
        return self.name

    class Meta:
        db_table = 'part_ssd'


class Ram(BasicInfo):
    memory_spec = models.CharField(max_length=10, null=True)
    memory_clerk = models.IntegerField(null=True)
    heat_sink = models.BooleanField(default=False, null=True)
    capacity = models.IntegerField(null=True)

    def __str__(self):
        return self.name

    class Meta:
        db_table = 'part_ram'


class Gpu(BasicInfo):
    needed_power = models.IntegerField(null=True)
    width = models.DecimalField(max_digits=7, decimal_places=2, null=True)
    thickness = models.DecimalField(max_digits=7, decimal_places=2, null=True)
    score = models.IntegerField(null=True)

    def __str__(self):
        return self.name

    class Meta:
        db_table = 'part_gpu'


class Case(BasicInfo):
    # 수용 가능한 보드 사이즈
    extended_atx = models.BooleanField(default=False, null=True)
    standard_atx = models.BooleanField(default=False, null=True)
    micro_atx = models.BooleanField(default=False, null=True)
    mini_itx = models.BooleanField(default=False, null=True)


    # 케이스 사이즈
    # cpu 쿨러 높이와 관련, 케이스의 정면 가로 길이
    width = models.DecimalField(max_digits=7, decimal_places=2, null=True)
    # gpu 길이와 관련, 케이스의 측면 가로 길이
    depth = models.DecimalField(max_digits=7, decimal_places=2, null=True)

    # 부품별 최대 수용 가능 사이즈
    max_power_depth = models.DecimalField(
        max_digits=7, decimal_places=2, null=True)  # 최대 파워 길이
    max_gpu_depth = models.DecimalField(
        max_digits=7, decimal_places=2, null=True)  # 최대 gpu 길이
    max_cooler_width = models.DecimalField(
        max_digits=7, decimal_places=2, null=True)  # 최대 쿨러 높이

    def __str__(self):
        return self.name

    class Meta:
        db_table = 'part_case'


class Power(BasicInfo):
    size = models.CharField(max_length=10, null=True)
    grade = models.CharField(max_length=20, null=True)
    output = models.IntegerField(null=True)
    depth = models.DecimalField(max_digits=7, decimal_places=2, null=True)
    free_warranty_period = models.IntegerField(null=True)

    def __str__(self):
        return self.name

    class Meta:
        db_table = 'part_power'


class Cooler(BasicInfo):
    form = models.CharField(max_length=20, null=True)
    free_warranty_period = models.IntegerField(null=True)
    height = models.DecimalField(max_digits=7, decimal_places=2, null=True)
    fan_count = models.IntegerField(null=True)
    max_fan_noise = models.DecimalField(
        max_digits=7, decimal_places=2, null=True)

    def __str__(self):
        return self.name

    class Meta:
        db_table = 'part_cooler'
