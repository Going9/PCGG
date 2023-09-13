from django.contrib import admin
from .models import PriceHistory, Cpu, Board, Ssd, Ram, Gpu, Case, Power, Cooler

# Register your models here.
models = [PriceHistory, Cpu, Board, Ssd, Ram, Gpu, Case, Power, Cooler]

for model in models:
    admin.site.register(model)
