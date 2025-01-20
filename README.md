# [Cluster Management Platform 'Violin' ⚡️](https://horizon-ui.com/horizon-ui-chakra-ts)
![version](https://img.shields.io/badge/version-0.0.1-blue.svg)

### [Develop Chronology](https://syyjbhjh7.notion.site/105daaf65d1280498847c9d22bf8c311?pvs=4)

## 🎉 Introduction
**Viola**는 Multi K8S Management Platform으로 많은 목표를 기반으로 꾸준히 개발될 제 사이드 프로젝트입니다.<br/>
어떤 클라우드 플랫폼에도 종속되지 않고 관리하며, 제 기준의 신기술들을 도입해보려 합니다.

## 🚀 Tech Stack 

### 개발 - **Spring Boot** + **Horizon UI**

**Spring Boot** 🌱
- **Java** & **Gradle**: 마이크로서비스 구축을 위한 강력한 Java 기반 프레임워크.
- **Micrometer**: 모니터링 및 가시성을 위한 메트릭 수집 라이브러리.

**Horizon UI** 🌟
- **React** & **Typescript**: 동적인 UI를 구축하기 위한 최신 프론트엔드 스택.
- **Zustand**: React를 위한 가벼운 상태 관리 라이브러리.

---

### 미들웨어 - **Redis**, **MariaDB**, **Tempo**, **Grafana** 🔧

**DBMS** 💾
- **MariaDB**: 강력하고 안정적인 관계형 데이터베이스 시스템.
- **Redis**: 캐싱과 세션 관리를 위한 인메모리 데이터 구조 저장소.

**트레이싱 및 모니터링** 📊
- **Tempo**: 마이크로서비스 요청을 추적할 수 있는 분산 트레이싱 시스템.
- **Grafana**: 모니터링 및 가시성을 위한 오픈 소스 플랫폼.

---

### 지원 기능 ✅
- **JWT Token 기반 로그인**: JSON Web Token을 활용한 안전한 로그인 시스템.
- **Kubeconfig 클러스터 연동**: Kubernetes 클러스터와의 원활한 연동.
- **Fabric8을 통한 K8S 기본 리소스 조회**: Kubernetes 리소스에 대한 기본 정보 조회.
- **상태 대시보드**: 리소스 상태를 실시간으로 모니터링하는 대시보드.

---

### 개발 중 기능 🔨
- **리소스 생성, 수정, 삭제**: Kubernetes 리소스의 전체 생애 주기 관리.
- **Prometheus 연동**: Prometheus를 통한 메트릭 조회 및 임계치 설정.
- **K8S Api-server 감사 로그**: API 요청에 대한 감사 및 보안 로그 기록.
- **최적의 Pod 리소스 계산**: Pod의 이상적인 리소스 할당 계산.
- **CRD 등록, Helm Release**: Custom Resource Definition 및 Helm 기반 배포 지원.

---

