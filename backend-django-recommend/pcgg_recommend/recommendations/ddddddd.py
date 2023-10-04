import sys

sys.path.append('/')

import os

os.environ.setdefault("DJANGO_SETTINGS_MODULE", "PCGG_recommend.settings")

import django

django.setup()


from recommendations.models import PeripheralSaved

rating = PeripheralSaved.objects.all()
print(rating)