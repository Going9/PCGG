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
      <td align="center"><a href="https://lab.ssafy.com/wnddnjs843"><img src="https://secure.gravatar.com/avatar/5a2d393458ff42eefa2d0a544d8a55e5?s=192&d=identicon" width="100px" alt=""/><br /><sub><b>BE,FE 팀원 : 장중원</b><br /><b>{역할}</b></sub></a><br /></td>
      <td align="center"><a href="https://lab.ssafy.com/ischar"><img src="https://secure.gravatar.com/avatar/d9939c7c2ac8a72ca0161df2dd6be413?s=192&d=identicon" width="100px" alt=""/><br /><sub><b>BE 팀원 : 차재호</b><br /><b>인프라/ 중고거래</b></sub></a><br /></td>
    </tr>
  </tbody>
</table>
