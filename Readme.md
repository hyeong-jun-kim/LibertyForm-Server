<!-- PROJECT LOGO -->
<br />
<div align="center">
    <img src="https://user-images.githubusercontent.com/45286570/209203950-c74081d1-2127-4800-ad24-73292b72c6b1.png" alt="Logo" width="100" height="100">
  </a>

  <h3 align="center">Liberty Form</h3>

  <p align="center">
    편리하고 자유로운 형식의 설문 플랫폼
    <br />
    카카오 엔터프라이즈 아카데미 설문 프로젝트
    <br />
    <br />
    <a href="https://www.notion.so/gachonkakao/Liberty-Form-9c8d419f55194b3eb249af4bb7a91091?pvs=4"><strong>LibertyForm 프로젝트 정리 페이지 »</strong></a> </br>
    <a href="https://www.notion.so/gachonkakao/dcd1674faca747aa990eaf26fe00e3cb?pvs=4"><strong>데일리 스크럼 일지 »</strong></a> </br>
    <br />
  </p>
</div>

<!-- 팀원 소개 -->
## 팀원 소개 (Error-it Team)
| 이름                                                  | 역할             |
| ----------------------------------------------------- | ---------------- |
| [김형준](https://github.com/hyeong-jun-kim)       | PM, Backend |
| [백우진](https://github.com/bwj0509) | Frontend |  
| [이가영](https://github.com/gayoung1115)     | Frontend |  
| [이상협](https://github.com/dltkdguq97)  | Machine Learing | 
| [한만규](https://github.com/bluesushi264)     | Frontend |  


<!-- ABOUT THE PROJECT -->
## Liberty Form 프로젝트 소개

자유롭고 편리한 설문 플랫폼을 구현하기 위해 실시된 프로젝트입니다. <br/>
일반적인 설문 플랫폼과 달리, 한 문항당 슬라이드 형식으로 설문이 실시되는 것이 특징입니다. <br/>

사용자는 자유로운 형식으로 설문지를 꾸밀 수 있으며, 설문 생성 및 수정, 설문 응답. 응답자 관리 및 분석, 설문지 링크 생성 및 발송 등 <br/>
설문에 관련된 주요 기능들을 이용하실 수 있습니다. <br/>

### 프로젝트 수행 기간 
2022년 9월 1일 ~ 2022년 12월 15일 (15주)

## 사용 기술
### 개발 환경
* ![Spring Boot][SpringBoot.icon]
* ![Redis][Redis.icon]
* ![MySQL][MySQL.icon]
* ![Kubenetis][Kubenetis.icon]

### CI & CD
* ![Jenkins][Jenkins.icon]
* ArgoCD

### 모니터링
* ![Prometheus][Prometheus.icon]
* ![Grafana][Grafana.icon]

## 테스트
* ![JUnit][JUnit.icon]
* Jmeter

### 협업 도구
* ![Jira][Jira.icon]
* ![Slack][Slack.icon]
* ![Notion][Notion.icon]

## 시스템 아키텍처
![image](https://user-images.githubusercontent.com/53989167/207321811-65129ad2-1fde-40ea-8dc0-b2de9ecd0ed3.png)
- **아키텍처 구성**
  - 3개의 노드와 3개의 React, Spring, Flask 서버 파드로 구성되었습니다.
  - public subnet, private subnet을 분리했으며, Bastion을 통해서만 private subnet에 접근 가능하게 설정했습니다.

- **CI&CD 파이프라인 구현**
  - Github push -> Jenkins gradle build & image build -> DockerHub Image Upload -> service & deployment.yml version update (github) -> ArgoCD Deployment (Auto Sync)
  - 배포 성공, 실패에 대해 알려주는 Slack 알림 기능을 구현했습니다.

- **데이터베이스 CDC 적용**
  - KEA 데이터 관리 기술에서 배운 CDC 기술을 해당 설문 프로젝트에 적용했습니다.

- **Object Storage**
  - 사진, 동영상 등 비정형 데이터를 관리하기 위해 Object Storage를 생성했습니다.
  - 외부에서 kakao i cloud Object Storage에 등록하기위해 제공되는 라이브러리가 없어, 이를 직접 구현했습니다.
  - IAM 계정을 통해 클라우드 계정의 권한을 분리했습니다.

- **모니터링**
  - Prometheus를 통해 각 노드의 실시간 리소스를 수집하고, Grafana를 통해 노드별 metrics를 알 수 있습니다.
  - AlarmManager으로 AlertRule을 정의 해, 특정 노드가 CPU 30% 이상 및 RAM 50% 이상 점유 시 Slack으로 알람이 가게하는 기능을 구현했습니다.
         
## CDC 아키텍처
![image](https://user-images.githubusercontent.com/53989167/207322275-89bfc8f9-01c6-4332-9e3c-bc4eab63fe1b.png)
- 데이터 관리기술에서 배운 CDC 기술을 설문 서비스에서 직접 적용하고 싶어, 해당 아키텍처를 설계하고 구현하였습니다.
- 운영 DB (Source DB)에서 DML이나 DDL 쿼리가 발생하면, 이와 연결된 Debezium Connector를 통해 로그가 수집되며 해당 내용을 카프카 토픽으로 발행합니다.
- CDC 역할을 수행하는 미들서버에서 해당 토픽을 consume 받아, payload를 분석해 각 타겟 DB로 실시간 마이그레이션이 진행됩니다.
- 카프카의 토픽으로 내용들이 전달되기 떄문에, druid에서 해당 토픽으로 받아 스키마를 생성하고 Superset으로 druid를 연결 해 실시간으로 데이터 시각화 정보를 수집할 수 있습니다.

## LibertyForm 관련 깃허브
<a href="https://github.com/Gachon-Kakao-Enterprise-Group-7/libertyForm-WEB"><strong>libertyForm-WEB</strong></a></br>
<a href="https://github.com/hyeong-jun-kim/libertyform-CDC"><strong>libertyForm-CDC</strong></a></br>

---
<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[RabbitMQ.icon]: https://img.shields.io/badge/rabbitmq-%23FF6600.svg?&style=for-the-badge&logo=rabbitmq&logoColor=white
[Redis.icon]: https://img.shields.io/badge/redis-%23DD0031.svg?&style=for-the-badge&logo=redis&logoColor=white
[SpringBoot.icon]: https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot
[Kubenetis.icon]: https://img.shields.io/badge/kubernetes-326ce5.svg?&style=for-the-badge&logo=kubernetes&logoColor=white
[MySQL.icon]: https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white

[ElasticSearch.icon]: https://img.shields.io/badge/Elastic_Search-005571?style=for-the-badge&logo=elasticsearch&logoColor=white
[FluentD.icon]: https://img.shields.io/badge/Fluentd-599CD0?style=for-the-badge&logo=fluentd&logoColor=white&labelColor=599CD0
[Kibana.icon]: https://img.shields.io/badge/Kibana-005571?style=for-the-badge&logo=Kibana&logoColor=white
[Prometheus.icon]: https://img.shields.io/badge/Prometheus-000000?style=for-the-badge&logo=prometheus&labelColor=000000
[Grafana.icon]: https://img.shields.io/badge/Grafana-F2F4F9?style=for-the-badge&logo=grafana&logoColor=orange&labelColor=F2F4F9

[Jenkins.icon]: https://img.shields.io/badge/Jenkins-D24939?style=for-the-badge&logo=Jenkins&logoColor=white

[JUnit.icon]: https://img.shields.io/badge/Junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white

[Jira.icon]: https://img.shields.io/badge/Jira-0052CC?style=for-the-badge&logo=Jira&logoColor=white
[Slack.icon]: https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=slack&logoColor=white
[Notion.icon]: https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=notion&logoColor=white

### Awards
🥇 <b>가천-카카오엔터프라이즈 SW아카데미 최우수 프로젝트 선정 </b>
