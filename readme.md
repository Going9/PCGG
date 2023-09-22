# 💻 PC.GG 💻
<b>PC.GG</b>는 용도, 예산 등을 고려한 <b>사용자 맞춤형 조립PC</b> 견적을 추천해주며 사용자 리뷰데이터 기반으로 <b>주변기기를 추천</b>해주는 웹 사이트입니다.

## 🌈 TEAM 무지개발
저희 <b>무지개발</b> 팀은 이렇게 프로젝트를 진행하고 있어요!
- 매일 아침 <b>스크럼 회의</b>를 통해 팀원간 개발 진행 상황 공유
- Git branch 전략 및 commit convention 수립
    - 배포는 `backend`, `frontend` 브랜치를 기준으로 진행
      - 추후 `crawling`, `recommend` 브랜치(가제) 추가 예정
    - 백엔드와 프론트엔드 개발은 각각 backend, frontend 브랜치에서 'feat/기능명'이름으로 새로운 브랜치 생성 후 개발
    - 코드 병합시 merge request를 먼저 진행
    - 2명 이상의 팀원이 reviewer로써 코드 리뷰
    - 리퀘스트 요청자는는 피드백 사항 수정
    - approve를 하게 되면 최종적으로 작성자 본인이 merge
    - [무지개발 Notion - Git Convention](https://www.notion.so/Git-Convention-d854c0488fdb4955a09f0dd4c750e192)
    - [무지개발 Notion - Merge Request Convention](https://www.notion.so/Merge-Request-Convention-e36cf62061e34b69940b068630c2c4b2)
- <b>기록하기</b>
    - 각자 프로젝트를 진행하면서 학습한 내용을 팀 노션에 작성하여 공유
    - [무지개발 Notion 바로가기](https://www.notion.so/Home-D206-da0cdf812e1244e7b990d1951ded90d2)

## 📌 주요 기능

[무지개발 Notion - 요구사항&기능명세](https://www.notion.so/0a7d57d857b54a33ad8d3420ffd2e4ed)

#### 유저 관련 
- 회원가입, 로그인, 마이페이지와 같은 유저관련 기능
#### 조립PC 견적추천
- 원하는 스펙(용도, 예산 등)을 입력하면 호환성, 성능 등을 고려하여 조립PC 견적 생성후 추천
#### 시뮬레이션 기능(부품 호환성 체크) 
- 부품 세트를 입력하면 호환성을 체크
#### 주변기기 추천 
- 사용자 리뷰 데이터를 기반으로 추천 알고리즘을 통해 주변기기 추천
#### 중고거래 
- 중고거래를 할 수 있는 게시판
####  공유마당 
- 사용자간 견적을 공유 할 수 있는 커뮤니티 게시판. 마이페이지, 시뮬레이션과 연동됨

## ⚙️ 기술스택
- `Frontend` - Vue
- `Backend` - Java, Springboot, Spring Security, Swagger, Gradle
- `DB` - MySQL, Redis
- `Crawling`, `Data analysis` -  Python, Django, Selenium
- `Deploy` - AWS EC2, S3, Nginx, Jenkins, Docker, Spring Cloud, Gateway, Eureka
- `Cooperation` - Jira, GitLab, Mattermost, Notion, Figma, ERDCloud

## 📈 ERD
![PC.GG](/uploads/c4588bd7e9a8a83136e7ceaf34d4c42d/PC.GG.png)

## 🔌아키텍처
![아키텍처](/uploads/491a90642fcc04d8cb048446b38cc916/아키텍처.png)

## 📅 일정
- 기획: 2023.08.28 ~ 2023.09.01 (1주)
- 개발: 2023.09.04 ~ ing

## 👪 팀원 및 역할
<table>
  <tbody>
    <tr>
      <td align="center"><a href="https://lab.ssafy.com/eorms96"><img src="https://secure.gravatar.com/avatar/d2abf68ca33213685faa6c92a097f27b?s=192&d=identicon" width="100px" alt=""/><br /><sub><b>BE 팀장 : 이대근</b><br /><b>DB / PC견적추천</b></sub></a><br /></td>
      <td align="center"><a href="https://lab.ssafy.com/ickyu777"><img src="https://secure.gravatar.com/avatar/cbfde9ced5f31bbf2e2c78bc12373d39?s=192&d=identicon" width="100px" alt=""/><br /><sub><b>BE 팀원 : 공익규</b><br /><b>{역할} </b></sub></a></td>
      <td align="center"><a href="https://lab.ssafy.com/ninth6764"><img src="https://secure.gravatar.com/avatar/f7f2e3c4798ce5d866d7ac74f214cab7?s=192&d=identicon" width="100px" alt=""/><br /><sub><b>FE 팀원 : 구본재</b><br /><b>{역할}</b></sub></a><br /></td>
      <td align="center"><a href="https://lab.ssafy.com/thdl9893"><img src="https://secure.gravatar.com/avatar/1dd3239aff334f6cbfaecacf92108958?s=192&d=identicon" width="100px" alt=""/><br /><sub><b>BE 팀원 : 김소이</b><br /><b>주변기기추천/ 공유마당</b></sub></a><br /></td>
     <tr/>
      <td align="center"><a href="https://lab.ssafy.com/poi1229"><img src="https://secure.gravatar.com/avatar/2f2f0289c3a6be08891f7aa080d47ca7?s=192&d=identicon" width="100px" alt=""/><br /><sub><b>FE 팀원 : 류성하</b><br /><b>주변기기/ 시뮬레이션 페이지</b></sub></a><br /></td>
      <td align="center"><a href="https://lab.ssafy.com/wnddnjs843"><img src="https://secure.gravatar.com/avatar/5a2d393458ff42eefa2d0a544d8a55e5?s=192&d=identicon" width="100px" alt=""/><br /><sub><b>BE,FE 팀원 : 장중원</b><br /><b>JWT 인증</b></sub></a><br /></td>
      <td align="center"><a href="https://lab.ssafy.com/ischar"><img src="https://secure.gravatar.com/avatar/d9939c7c2ac8a72ca0161df2dd6be413?s=192&d=identicon" width="100px" alt=""/><br /><sub><b>BE,FE 팀원 : 차재호</b><br /><b>인프라/ 중고거래</b></sub></a><br /></td>
    </tr>
  </tbody>
</table>

## 📫 배포 내용
#### Nginx
- 설치
```
# nginx 도커 이미지 받기
docker pull nginx

# nginx 컨테이너 만들고 실행하기 
docker run --name reverse-proxy -p 8082:22 -p 80:80 -p 443:443 -d nginx:latest 
``` 
- default.conf
```
server {
        listen 443 ssl default_server;
        listen [::]:443 ssl default_server;

        ssl_certificate /etc/ssl/full_chain.crt;
        ssl_certificate_key /etc/ssl/private.key;

        server_name pcgg.kro.kr;

        location / {
								# vue.js 경로
                root /var/www/dist;
                index index.html index.htm;
        }

        location /api {
                proxy_set_header Host $host;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_pass http://pcgg.kro.kr:8001/api;


        }
}

server {
        listen 80;
        server_name pcgg.kro.kr;

        location / {
                return 301 https://$server_name$request_uri;
        }

}
```
#### Jenkins
```
# 8080번 포트 허용
sudo ufw allow 8080

# 젠킨스 도커 이미지 받기
sudo docker pull jenkins/jenkins:lts

# 젠킨스 컨테이너 만들고 실행
sudo docker run --privileged -d -p 8080:8080 -p 50000:50000 --name jenkins jenkins/jenkins:lts

# 젠킨스 초기 관리자 비밀번호 확인
sudo docker exec -it jenkins /bin/bash
cat /var/jenkins_home/secrets/initialAdminPassword
```
#### Spring Cloud Gateway
```
pipeline {
    agent any

    tools {
        jdk 'jdk-17'
        gradle 'gradle 8.2.1'
    }
    environment {
        // build info
        BACKEND_BUILD_PATH = '/var/jenkins_home/workspace/jenkins-gateway/gateway' // jar 파일을 빌드할 경로
        JAR_FILE_NAME = 'server'

        // gitlab
        CLONE_URL = 'https://lab.ssafy.com/s09-bigdata-recom-sub2/S09P22D206'
        TARGET_BRANCH = 'gateway'
        GITLAB_CREDENTIAL = 'jenkins-login'
        
				// host machine info
        USER_NAME = 'ubuntu' // 호스트 머신의 유저
        PUBLIC_IP = '172.26.3.182' // 호스트 머신의 외부 IP
        PORT_NUMBER = '8000' // 컨테이너 내부의 포트번호와 매핑할 host 머신의 포트번호
        SSH_KEY = 'ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQDllP3eitSeEiauVucdYVgmIbdfLlUdM7j1xcPQgMPcB0kkqszg621iTlTllloA634A9u69vCcU64aaHgjeZ3uyL8qif+MLw/0AWEuHsBBS3XOo8FV7wn3po+v0M8wA5xjxPGLoDk+3SlEC8EbxuJhRZ9Cct53/e0AZPPtNgOIgZ096hjiSQI8JG7LZxY2GXpuwkthd3d/UwmOnACwp0oWe/9FhmGqJGD7le/molZkMHsTyeGHvkURZ6bjzU34N7wQVUGSMDtVaKL5F/P5Efs+atyIbyZfGd7l5pjHqB5h2Zrs0v6cdFVSJ1MPi1A28ke+mahhJehQIpxPmE4BFXsXIKch2K4VCwZm53pE9ZMKGCVOwvAtk8A6jK5wta20GHF2JfJT3GlYD8njUqQZd0cbGcBWgyVvb8VEJdlwI9DEwOvEjnavyX7T8D97cz7O8zstxbt7GeWvQvtgItGnMs6vgX5rXNKchihU0v0sYfcsjL6tGsD7m+G8G5jQ2mDiY4JU= ubuntu@ip-172-26-3-182'

        // docker info
        BACKEND_BUILD_IMAGE_NAME = 'pcgg-backend-gateway'
        TAG_NAME = 'latest'
        BACKEND_CONTAINER_NAME = 'backend-gateway'
        BACKEND_CONTAINER_PORT_NUMBER = 8001

        // mattermost info
        END_POINT = 'https://meeting.ssafy.com/hooks/mux5zm9a83878daynwqms8ddty'
        CHANNEL = '#gw--jenkins'
    }

    stages {

        stage('Prepare') { // 저장소로부터 clone하는 단계
            steps {
                echo 'Cloning Repository'
                git credentialsId: GITLAB_CREDENTIAL,
                url: CLONE_URL,
                branch: TARGET_BRANCH
            }
            post {
                success {
                    echo 'Successfully Cloned Repository'
                }
                failure {
                    error 'This pipeline stops here...'
                }
            }
        }

        stage('Dowload application.yml') {
            steps {
                echo 'Download application.yml'
                withCredentials([file(credentialsId: 'secret-gateway', variable: 'SpringConfigFile')]) {
                    script {
                        sh '''
                        sudo cp $SpringConfigFile gateway/src/main/resources/application.yml
                        '''
                    }
                }
            }
        }

        stage('Build') { // jar 파일을 빌드하는 단계 
            steps {
                sh  '''
                    echo "Build gateway"
                    cd gateway
                    sudo su
                    chmod +x gradlew
                    sudo ./gradlew clean build -x test

                '''
            }
            post {
                success {
                    echo 'Successfully Build Server'
                }
                failure {
                    error 'This pipeline stops here...'
                }
            }
        }

        stage('Send Build') { // Build 파일들을 우분투(EC2)로 전송하는 단계 
            steps {
                sshPublisher(publishers: [sshPublisherDesc(configName: 'ubuntu', transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: ' ', execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '/remote/gateway/', remoteDirectorySDF: false, removePrefix: 'gateway/build/libs', sourceFiles: 'gateway/build/libs/pcgg-0.0.1-SNAPSHOT.jar')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])
            }
            
            post {
                success {
                    echo 'Successfully Send Build'
                }
                failure {
                    error 'This pipeline stops here...'
                }
            }
        }

        stage('Build Docker & Run') { // Dockerfile을 빌드하여 docker 이미지를 생성하고 실행하는 단계
            steps {
                echo 'Build Docker'
                sshagent(credentials: ['ubuntu']) {
                    script {
                    // 우분투(EC2)에서 실행할 command 스크립트
                    // 1. 빌드 파일 전송
                    // 2. 빌드 파일을 이용해 Docker 이미지 생성
                    // 3. 우분투(EC2)에서 실행시킬 컨테이너의 이름과 동일한 컨테이너가 있다면 그 컨테이너를 종료하고 삭제
                    // 4. Docker 이미지를 설정한 컨테이너이름으로 실행
                    def sshCommand = """
                        set -e
                        if sudo docker ps -a --filter name=${BACKEND_CONTAINER_NAME} | grep -q .; then
                            sudo docker stop ${BACKEND_CONTAINER_NAME} || true
                            sudo docker rm ${BACKEND_CONTAINER_NAME} || true
                            sudo docker rmi ${BACKEND_BUILD_IMAGE_NAME} || true

                        fi
                        cd remote/gateway
                        sudo docker build -t ${BACKEND_BUILD_IMAGE_NAME} .
                        sudo docker run -d --name ${BACKEND_CONTAINER_NAME} -p ${BACKEND_CONTAINER_PORT_NUMBER}:${BACKEND_CONTAINER_PORT_NUMBER} ${BACKEND_BUILD_IMAGE_NAME}
                    """

                    def remoteScriptFile = 'remote_script.sh' // 스크립트 파일 명
                    writeFile file: remoteScriptFile, text: sshCommand // 스크립트 파일 생성
                    sh "chmod +x ${remoteScriptFile}" // 스크립트 파일에 실행 권한 주기

                    // 원격실행 커멘드(scp : 파일전송 프로토콜(스크립트를 전송) & ssh : 배포서버에 원격으로 접속하기 위한 명령어 & sm : 스크립트 삭제 명령어(중복 제거를 위한 명령어))
                    def remoteCommand = "scp -o StrictHostKeyChecking=no ${remoteScriptFile} ${USER_NAME}@${PUBLIC_IP}:${remoteScriptFile} && ssh -o StrictHostKeyChecking=no ${USER_NAME}@${PUBLIC_IP} 'bash ./${remoteScriptFile}' && rm ${remoteScriptFile}"
                    sh remoteCommand // 커맨드 실행
                            
                    }
                }
            }
        }  
    }
    post {
        success {
            script {
                def Author_ID = sh(script: "git show -s --pretty=%an", returnStdout: true).trim()
                def Author_Name = sh(script: "git show -s --pretty=%ae", returnStdout: true).trim()
                mattermostSend (color: 'good',
                message: ":white_check_mark: Success: ${env.JOB_NAME} #${env.BUILD_NUMBER} by ${Author_ID}(${Author_Name}\\n(<${env.BUILD_URL}|Details>)",
                endpoint: END_POINT,
                channel: CHANNEL
                )
            }
        }
        failure {
    	    script {
                def Author_ID = sh(script: "git show -s --pretty=%an", returnStdout: true).trim()
                def Author_Name = sh(script: "git show -s --pretty=%ae", returnStdout: true).trim()
                mattermostSend (color: 'danger',
                message: ":x: Failure: ${env.JOB_NAME} #${env.BUILD_NUMBER} by ${Author_ID}(${Author_Name})\\n(<${env.BUILD_URL}|Details>)",
                endpoint: END_POINT,
                channel: CHANNEL
                )
            }
        }
    }
}
```

#### Netflix Eureka
```
pipeline {
    agent any

    tools {
        jdk 'jdk-17'
        gradle 'gradle 8.2.1'
    }
    environment {
        // build info
        BACKEND_BUILD_PATH = '/var/jenkins_home/workspace/jenkins-eureka/backend-eureka' // jar 파일을 빌드할 경로
        JAR_FILE_NAME = 'server'

        // gitlab
        CLONE_URL = 'https://lab.ssafy.com/s09-bigdata-recom-sub2/S09P22D206'
        TARGET_BRANCH = 'eureka'
        GITLAB_CREDENTIAL = 'jenkins-login'
        
				// host machine info
        USER_NAME = 'ubuntu' // 호스트 머신의 유저
        PUBLIC_IP = '172.26.3.182' // 호스트 머신의 외부 IP
        PORT_NUMBER = '8000' // 컨테이너 내부의 포트번호와 매핑할 host 머신의 포트번호
        SSH_KEY = 'ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQDllP3eitSeEiauVucdYVgmIbdfLlUdM7j1xcPQgMPcB0kkqszg621iTlTllloA634A9u69vCcU64aaHgjeZ3uyL8qif+MLw/0AWEuHsBBS3XOo8FV7wn3po+v0M8wA5xjxPGLoDk+3SlEC8EbxuJhRZ9Cct53/e0AZPPtNgOIgZ096hjiSQI8JG7LZxY2GXpuwkthd3d/UwmOnACwp0oWe/9FhmGqJGD7le/molZkMHsTyeGHvkURZ6bjzU34N7wQVUGSMDtVaKL5F/P5Efs+atyIbyZfGd7l5pjHqB5h2Zrs0v6cdFVSJ1MPi1A28ke+mahhJehQIpxPmE4BFXsXIKch2K4VCwZm53pE9ZMKGCVOwvAtk8A6jK5wta20GHF2JfJT3GlYD8njUqQZd0cbGcBWgyVvb8VEJdlwI9DEwOvEjnavyX7T8D97cz7O8zstxbt7GeWvQvtgItGnMs6vgX5rXNKchihU0v0sYfcsjL6tGsD7m+G8G5jQ2mDiY4JU= ubuntu@ip-172-26-3-182'

        // docker info
        BACKEND_BUILD_IMAGE_NAME = 'pcgg-backend-eureka'
        TAG_NAME = 'latest'
        BACKEND_CONTAINER_NAME = 'backend-eureka'
        BACKEND_CONTAINER_PORT_NUMBER = 8761

        // mattermost info
        END_POINT = 'https://meeting.ssafy.com/hooks/mux5zm9a83878daynwqms8ddty'
        CHANNEL = '#gw--jenkins'
    }

    stages {

        stage('Prepare') { // 저장소로부터 clone하는 단계
            steps {
                echo 'Cloning Repository'
                git credentialsId: GITLAB_CREDENTIAL,
                url: CLONE_URL,
                branch: TARGET_BRANCH
            }
            post {
                success {
                    echo 'Successfully Cloned Repository'
                }
                failure {
                    error 'This pipeline stops here...'
                }
            }
        }

        stage('Dowload application.yml') {
            steps {
                echo 'Download application.yml'
                withCredentials([file(credentialsId: 'secret-eureka', variable: 'SpringConfigFile')]) {
                    script {
                        sh '''
                        sudo cp $SpringConfigFile backend-eureka/src/main/resources/application.yml
                        '''
                    }
                }
            }
        }

        stage('Build') { // jar 파일을 빌드하는 단계 
            steps {
                sh  '''
                    echo "Build eureka"
                    cd backend-eureka
                    sudo su
                    chmod +x gradlew
                    sudo ./gradlew clean build -x test

                '''
            }
            post {
                success {
                    echo 'Successfully Build Server'
                }
                failure {
                    error 'This pipeline stops here...'
                }
            }
        }

        stage('Send Build') { // Build 파일들을 우분투(EC2)로 전송하는 단계 
            steps {
                sshPublisher(publishers: [sshPublisherDesc(configName: 'ubuntu', transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: ' ', execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '/remote/backend-eureka/', remoteDirectorySDF: false, removePrefix: 'backend-eureka/build/libs', sourceFiles: 'backend-eureka/build/libs/pcgg-0.0.1-SNAPSHOT.jar')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])
            }
            
            post {
                success {
                    echo 'Successfully Send Build'
                }
                failure {
                    error 'This pipeline stops here...'
                }
            }
        }

        stage('Build Docker & Run') { // Dockerfile을 빌드하여 docker 이미지를 생성하고 실행하는 단계
            steps {
                echo 'Build Docker'
                sshagent(credentials: ['ubuntu']) {
                    script {
                    // 우분투(EC2)에서 실행할 command 스크립트
                    // 1. 빌드 파일 전송
                    // 2. 빌드 파일을 이용해 Docker 이미지 생성
                    // 3. 우분투(EC2)에서 실행시킬 컨테이너의 이름과 동일한 컨테이너가 있다면 그 컨테이너를 종료하고 삭제
                    // 4. Docker 이미지를 설정한 컨테이너이름으로 실행
                    def sshCommand = """
                        set -e
                        if sudo docker ps -a --filter name=${BACKEND_CONTAINER_NAME} | grep -q .; then
                            sudo docker stop ${BACKEND_CONTAINER_NAME} || true
                            sudo docker rm ${BACKEND_CONTAINER_NAME} || true
                            sudo docker rmi ${BACKEND_BUILD_IMAGE_NAME} || true

                        fi
                        cd remote/backend-eureka
                        sudo docker build -t ${BACKEND_BUILD_IMAGE_NAME} .
                        sudo docker run -d --name ${BACKEND_CONTAINER_NAME} -p ${BACKEND_CONTAINER_PORT_NUMBER}:${BACKEND_CONTAINER_PORT_NUMBER} ${BACKEND_BUILD_IMAGE_NAME}
                    """

                    def remoteScriptFile = 'remote_script.sh' // 스크립트 파일 명
                    writeFile file: remoteScriptFile, text: sshCommand // 스크립트 파일 생성
                    sh "chmod +x ${remoteScriptFile}" // 스크립트 파일에 실행 권한 주기

                    // 원격실행 커멘드(scp : 파일전송 프로토콜(스크립트를 전송) & ssh : 배포서버에 원격으로 접속하기 위한 명령어 & sm : 스크립트 삭제 명령어(중복 제거를 위한 명령어))
                    def remoteCommand = "scp -o StrictHostKeyChecking=no ${remoteScriptFile} ${USER_NAME}@${PUBLIC_IP}:${remoteScriptFile} && ssh -o StrictHostKeyChecking=no ${USER_NAME}@${PUBLIC_IP} 'bash ./${remoteScriptFile}' && rm ${remoteScriptFile}"
                    sh remoteCommand // 커맨드 실행
                            
                    }
                }
            }
        }  
    }
    post {
        success {
            script {
                def Author_ID = sh(script: "git show -s --pretty=%an", returnStdout: true).trim()
                def Author_Name = sh(script: "git show -s --pretty=%ae", returnStdout: true).trim()
                mattermostSend (color: 'good',
                message: ":white_check_mark: Success: ${env.JOB_NAME} #${env.BUILD_NUMBER} by ${Author_ID}(${Author_Name}\\n(<${env.BUILD_URL}|Details>)",
                endpoint: END_POINT,
                channel: CHANNEL
                )
            }
        }
        failure {
    	    script {
                def Author_ID = sh(script: "git show -s --pretty=%an", returnStdout: true).trim()
                def Author_Name = sh(script: "git show -s --pretty=%ae", returnStdout: true).trim()
                mattermostSend (color: 'danger',
                message: ":x: Failure: ${env.JOB_NAME} #${env.BUILD_NUMBER} by ${Author_ID}(${Author_Name})\\n(<${env.BUILD_URL}|Details>)",
                endpoint: END_POINT,
                channel: CHANNEL
                )
            }
        }
    }
}
```

#### FE | Vue.js
```
pipeline {
    agent any

    tools {
        nodejs 'nodejs 16.18.1'
    }
    environment {
        // build info
        FRONTEND_BUILD_PATH = '/var/jenkins_home/workspace/jenkins/frontend' // jar 파일을 빌드할 경로
        JAR_FILE_NAME = 'server'

        // gitlab
        CLONE_URL = 'https://lab.ssafy.com/s09-bigdata-recom-sub2/S09P22D206'
        TARGET_BRANCH = 'frontend'
        GITLAB_CREDENTIAL = 'jenkins-login'
        
				// host machine info
        USER_NAME = 'root' // 호스트 머신의 유저
        PUBLIC_IP = '172.17.0.4' // 호스트 머신의 외부 IP
        PORT_NUMBER = '8000' // 컨테이너 내부의 포트번호와 매핑할 host 머신의 포트번호
        SSH_KEY = 'ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQDllP3eitSeEiauVucdYVgmIbdfLlUdM7j1xcPQgMPcB0kkqszg621iTlTllloA634A9u69vCcU64aaHgjeZ3uyL8qif+MLw/0AWEuHsBBS3XOo8FV7wn3po+v0M8wA5xjxPGLoDk+3SlEC8EbxuJhRZ9Cct53/e0AZPPtNgOIgZ096hjiSQI8JG7LZxY2GXpuwkthd3d/UwmOnACwp0oWe/9FhmGqJGD7le/molZkMHsTyeGHvkURZ6bjzU34N7wQVUGSMDtVaKL5F/P5Efs+atyIbyZfGd7l5pjHqB5h2Zrs0v6cdFVSJ1MPi1A28ke+mahhJehQIpxPmE4BFXsXIKch2K4VCwZm53pE9ZMKGCVOwvAtk8A6jK5wta20GHF2JfJT3GlYD8njUqQZd0cbGcBWgyVvb8VEJdlwI9DEwOvEjnavyX7T8D97cz7O8zstxbt7GeWvQvtgItGnMs6vgX5rXNKchihU0v0sYfcsjL6tGsD7m+G8G5jQ2mDiY4JU= ubuntu@ip-172-26-3-182'

        // docker info
        BACKEND_BUILD_IMAGE_NAME = 'pcgg-frontend'
        TAG_NAME = 'latest'
        FRONTEND_CONTAINER_NAME = 'backend-frontend'
        FRONTEND_CONTAINER_PORT_NUMBER = 3000

        // mattermost info
        END_POINT = 'https://meeting.ssafy.com/hooks/mux5zm9a83878daynwqms8ddty'
        CHANNEL = '#fe--jenkins'
    }

    stages {

        stage('Prepare') { // 저장소로부터 clone하는 단계
            steps {
                echo 'Cloning Repository'
                sh '''
                    sudo rm -r frontend
                '''
                git credentialsId: GITLAB_CREDENTIAL,
                url: CLONE_URL,
                branch: TARGET_BRANCH
            }
            post {
                success {
                    echo 'Successfully Cloned Repository'
                }
                failure {
                    error 'This pipeline stops here...'
                }
            }
        }

        stage('Download .env.production') { // .env.production을 download하는 단계
            steps {
                echo 'Download .env.production'
                withCredentials([file(credentialsId: 'secret-vue', variable: 'VueConfigFile')]) {
                    script {
                        sh '''
                        sudo cp $VueConfigFile frontend/.env.production
                        '''
                    }
                }
            }
        }

        stage('Build') { // build 파일을 빌드하는 단계 
            steps {
                sh  '''
                    echo "Build frontend"
                    cd frontend
                    sudo su
                    sudo rm -r /var/jenkins_home/workspace/build 
                    mkdir /var/jenkins_home/workspace/build
                    sudo npm install
                    sudo npm run build
                '''
            }
            post {
                success {
                    echo 'Successfully Build Server'
                }
                failure {
                    error 'This pipeline stops here...'
                }
            }
        }

        stage('Send Build') { // Build 파일들을 우분투(EC2)로 전송하는 단계 
            steps {
                sshPublisher(publishers: [sshPublisherDesc(configName: 'nginx', transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: ' ', execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '/', remoteDirectorySDF: false, removePrefix: 'frontend', sourceFiles: 'frontend/dist/**/*')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])
            }
            
            post {
                success {
                    echo 'Successfully Send Build'
                }
                failure {
                    error 'This pipeline stops here...'
                }
            }
        }
    }

    post {
        success {
            script {
                def Author_ID = sh(script: "git show -s --pretty=%an", returnStdout: true).trim()
                def Author_Name = sh(script: "git show -s --pretty=%ae", returnStdout: true).trim()
                mattermostSend (color: 'good',
                message: ":white_check_mark: Success: ${env.JOB_NAME} #${env.BUILD_NUMBER} by ${Author_ID}(${Author_Name}\\n(<${env.BUILD_URL}|Details>)",
                endpoint: END_POINT,
                channel: CHANNEL
                )
            }
        }
        failure {
    	    script {
                def Author_ID = sh(script: "git show -s --pretty=%an", returnStdout: true).trim()
                def Author_Name = sh(script: "git show -s --pretty=%ae", returnStdout: true).trim()
                mattermostSend (color: 'danger',
                message: ":x: Failure: ${env.JOB_NAME} #${env.BUILD_NUMBER} by ${Author_ID}(${Author_Name})\\n(<${env.BUILD_URL}|Details>)",
                endpoint: END_POINT,
                channel: CHANNEL
                )
            }
        }
    }
}
```

#### BE | Spring Boot 
```
pipeline {
    agent any

    tools {
        jdk 'jdk-17'
        gradle 'gradle 8.2.1'
        nodejs 'nodejs 16.18.1'
    }
    environment {
        // build info
        BACKEND_BUILD_PATH = '/var/jenkins_home/workspace/jenkins/backend-spring' // jar 파일을 빌드할 경로
        JAR_FILE_NAME = 'server'

        // gitlab
        CLONE_URL = 'https://lab.ssafy.com/s09-bigdata-recom-sub2/S09P22D206'
        TARGET_BRANCH = 'backend'
        GITLAB_CREDENTIAL = 'jenkins-login'
        
				// host machine info
        USER_NAME = 'ubuntu' // 호스트 머신의 유저
        PUBLIC_IP = '172.26.3.182' // 호스트 머신의 외부 IP
        PORT_NUMBER = '8000' // 컨테이너 내부의 포트번호와 매핑할 host 머신의 포트번호
        SSH_KEY = 'ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQDllP3eitSeEiauVucdYVgmIbdfLlUdM7j1xcPQgMPcB0kkqszg621iTlTllloA634A9u69vCcU64aaHgjeZ3uyL8qif+MLw/0AWEuHsBBS3XOo8FV7wn3po+v0M8wA5xjxPGLoDk+3SlEC8EbxuJhRZ9Cct53/e0AZPPtNgOIgZ096hjiSQI8JG7LZxY2GXpuwkthd3d/UwmOnACwp0oWe/9FhmGqJGD7le/molZkMHsTyeGHvkURZ6bjzU34N7wQVUGSMDtVaKL5F/P5Efs+atyIbyZfGd7l5pjHqB5h2Zrs0v6cdFVSJ1MPi1A28ke+mahhJehQIpxPmE4BFXsXIKch2K4VCwZm53pE9ZMKGCVOwvAtk8A6jK5wta20GHF2JfJT3GlYD8njUqQZd0cbGcBWgyVvb8VEJdlwI9DEwOvEjnavyX7T8D97cz7O8zstxbt7GeWvQvtgItGnMs6vgX5rXNKchihU0v0sYfcsjL6tGsD7m+G8G5jQ2mDiY4JU= ubuntu@ip-172-26-3-182'

        // docker info
        BACKEND_BUILD_IMAGE_NAME = 'pcgg-backend-spring'
        TAG_NAME = 'latest'
        BACKEND_CONTAINER_NAME = 'backend-spring'
        BACKEND_CONTAINER_PORT_NUMBER = 30000

        // mattermost info
        END_POINT = 'https://meeting.ssafy.com/hooks/mux5zm9a83878daynwqms8ddty'
        CHANNEL = '#be--jenkins'
    }

    stages {

        stage('Prepare') { // 저장소로부터 clone하는 단계
            steps {
                echo 'Cloning Repository'
                git credentialsId: GITLAB_CREDENTIAL,
                url: CLONE_URL,
                branch: TARGET_BRANCH
            }
            post {
                success {
                    echo 'Successfully Cloned Repository'
                }
                failure {
                    error 'This pipeline stops here...'
                }
            }
        }

        stage('Dowload application.yml') {
            steps {
                echo 'Download application.yml'
                withCredentials([file(credentialsId: 'secret-id', variable: 'SpringConfigFile')]) {
                    script {
                        sh '''
                        sudo cp $SpringConfigFile backend-spring/src/main/resources/application.yml
                        '''
                    }
                }
            }
        }

        stage('Build') { // jar 파일을 빌드하는 단계 
            steps {
                sh  '''
                    echo "Build backend"
                    cd backend-spring
                    sudo su
                    sudo rm -r /var/jenkins_home/workspace/build 
                    mkdir /var/jenkins_home/workspace/build
                    chmod +x gradlew
                    ./gradlew clean build -x test

                '''
            }
            post {
                success {
                    echo 'Successfully Build Server'
                }
                failure {
                    error 'This pipeline stops here...'
                }
            }
        }

        stage('Send Build') { // Build 파일들을 우분투(EC2)로 전송하는 단계 
            steps {
                sshPublisher(publishers: [sshPublisherDesc(configName: 'ubuntu', transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: ' ', execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '/remote/backend-spring/', remoteDirectorySDF: false, removePrefix: 'backend-spring/build/libs', sourceFiles: 'backend-spring/build/libs/pcgg-0.0.1-SNAPSHOT.jar')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])
            }
            
            post {
                success {
                    echo 'Successfully Send Build'
                }
                failure {
                    error 'This pipeline stops here...'
                }
            }
        }

        stage('Build Docker & Run') { // Dockerfile을 빌드하여 docker 이미지를 생성하고 실행하는 단계
            steps {
                echo 'Build Docker'
                sshagent(credentials: ['ubuntu']) {
                    script {
                    // 우분투(EC2)에서 실행할 command 스크립트
                    // 1. 빌드 파일 전송
                    // 2. 빌드 파일을 이용해 Docker 이미지 생성
                    // 3. 우분투(EC2)에서 실행시킬 컨테이너의 이름과 동일한 컨테이너가 있다면 그 컨테이너를 종료하고 삭제
                    // 4. Docker 이미지를 설정한 컨테이너이름으로 실행
                    def sshCommand = """
                        set -e
                        if sudo docker ps -a --filter name=${BACKEND_CONTAINER_NAME} | grep -q .; then
                            sudo docker stop ${BACKEND_CONTAINER_NAME} || true
                            sudo docker rm ${BACKEND_CONTAINER_NAME} || true
                            sudo docker rmi ${BACKEND_BUILD_IMAGE_NAME} || true

                        fi
                        cd remote/backend-spring
                        sudo docker build -t ${BACKEND_BUILD_IMAGE_NAME} .
                        sudo docker run -d --name ${BACKEND_CONTAINER_NAME} -p ${BACKEND_CONTAINER_PORT_NUMBER}:${BACKEND_CONTAINER_PORT_NUMBER} ${BACKEND_BUILD_IMAGE_NAME}
                    """

                    def remoteScriptFile = 'remote_script.sh' // 스크립트 파일 명
                    writeFile file: remoteScriptFile, text: sshCommand // 스크립트 파일 생성
                    sh "chmod +x ${remoteScriptFile}" // 스크립트 파일에 실행 권한 주기

                    // 원격실행 커멘드(scp : 파일전송 프로토콜(스크립트를 전송) & ssh : 배포서버에 원격으로 접속하기 위한 명령어 & sm : 스크립트 삭제 명령어(중복 제거를 위한 명령어))
                    def remoteCommand = "scp -o StrictHostKeyChecking=no ${remoteScriptFile} ${USER_NAME}@${PUBLIC_IP}:${remoteScriptFile} && ssh -o StrictHostKeyChecking=no ${USER_NAME}@${PUBLIC_IP} 'bash ./${remoteScriptFile}' && rm ${remoteScriptFile}"
                    sh remoteCommand // 커맨드 실행
                            
                    }
                }
            }
        }  
    }
    post {
        success {
            script {
                def Author_ID = sh(script: "git show -s --pretty=%an", returnStdout: true).trim()
                def Author_Name = sh(script: "git show -s --pretty=%ae", returnStdout: true).trim()
                mattermostSend (color: 'good',
                message: ":white_check_mark: Success: ${env.JOB_NAME} #${env.BUILD_NUMBER} by ${Author_ID}(${Author_Name}\\n(<${env.BUILD_URL}|Details>)",
                endpoint: END_POINT,
                channel: CHANNEL
                )
            }
        }
        failure {
    	    script {
                def Author_ID = sh(script: "git show -s --pretty=%an", returnStdout: true).trim()
                def Author_Name = sh(script: "git show -s --pretty=%ae", returnStdout: true).trim()
                mattermostSend (color: 'danger',
                message: ":x: Failure: ${env.JOB_NAME} #${env.BUILD_NUMBER} by ${Author_ID}(${Author_Name})\\n(<${env.BUILD_URL}|Details>)",
                endpoint: END_POINT,
                channel: CHANNEL
                )
            }
        }
    }
}
```

