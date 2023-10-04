# Python 이미지를 기반으로 합니다.
FROM python:3

# 작업 디렉토리를 설정합니다.
WORKDIR /usr/src/app

# requirements.txt 파일을 Docker 이미지로 복사합니다.
COPY requirements.txt ./

# .env 파일을 Docker 이미지로 복사합니다.
COPY .env ./

# requirements.txt 파일에 있는 모든 패키지를 설치합니다.
RUN pip install --no-cache-dir -r requirements.txt

# Django 애플리케이션 코드를 Docker 이미지로 복사합니다.
COPY . .

# Django 서버를 실행합니다.
CMD [ "python", "manage.py", "runserver", "0.0.0.0:8000" ]
