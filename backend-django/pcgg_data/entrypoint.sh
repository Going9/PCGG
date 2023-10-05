#!/bin/sh

# Django 서버를 백그라운드에서 실행합니다.
gunicorn PCGG_data.wsgi:application --bind 0.0.0.0:8002 &

# Celery Beat를 실행합니다.
exec celery -A crawlers.tasks beat --loglevel=info

