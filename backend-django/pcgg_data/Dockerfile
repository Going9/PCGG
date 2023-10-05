# Ubuntu 이미지를 기반으로 합니다.
FROM ubuntu:latest

# 작업 디렉토리를 설정합니다.
WORKDIR /usr/src/app

# 필요한 패키지들을 설치합니다.
RUN apt-get update && apt-get install -y python3.11 python3-pip

# requirements.txt 파일을 Docker 이미지로 복사합니다.
COPY requirements.txt ./

# .env 파일을 Docker 이미지로 복사합니다.
COPY .env ./

# requirements.txt 파일에 있는 모든 패키지를 설치합니다.
RUN pip3 install --no-cache-dir -r requirements.txt

# 크롬 브라우저를 설치합니다.
RUN wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
RUN apt install ./google-chrome-stable_current_amd64.deb

# Django 애플리케이션 코드를 Docker 이미지로 복사합니다.
COPY . .

# entrypoint.sh 스크립트에 실행 권한을 부여합니다.
RUN chmod +x /usr/src/app/entrypoint.sh

# entrypoint.sh 스크립트를 실행합니다.
CMD ["/usr/src/app/entrypoint.sh"]
