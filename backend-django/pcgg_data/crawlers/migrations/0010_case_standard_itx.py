# Generated by Django 4.2.5 on 2023-09-21 07:06

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('crawlers', '0009_alter_ssd_capacity'),
    ]

    operations = [
        migrations.AddField(
            model_name='case',
            name='standard_itx',
            field=models.BooleanField(default=False, null=True),
        ),
    ]