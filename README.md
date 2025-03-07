<p align="center">
  <img src="https://readme-typing-svg.herokuapp.com?size=22&duration=3000&color=F75C7E&center=true&vCenter=true&width=500&height=40&lines=🚀+Welcome+to+Chickensajo!;🍔+Delivery+Service+Project&font=Fira+Code" />
</p>

# 🚀 아웃소싱 프로젝트 - 배달 어플리케이션
이 프로젝트는 **고객과 사장님을 위한 배달 애플리케이션**입니다.  
사장님은 가게를 등록하고 메뉴를 관리하며, 고객 리뷰에 댓글을 남길 수 있습니다.  
고객은 장바구니를 이용해 원하는 메뉴를 주문하고, 주문 완료 후 리뷰를 작성할 수 있습니다.  
또한, JWT 기반 인증을 적용하여 보안을 강화하고, 주문 상태 변경 시 로깅 기능을 제공합니다.

![Java](https://img.shields.io/badge/Java-17-blue?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.2-green?style=for-the-badge&logo=springboot&logoColor=white)
![JPA](https://img.shields.io/badge/JPA-Hibernate%20ORM-%236DB33F?style=for-the-badge&logo=hibernate&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-Authentication-%233A3A3A?style=for-the-badge&logo=jsonwebtokens&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-6.6.5.Final-%236DB33F?style=for-the-badge&logo=hibernate&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8.0-orange?style=for-the-badge&logo=mysql&logoColor=white)
[![GitHub](https://img.shields.io/badge/GitHub-Organization-black?style=for-the-badge&logo=github&logoColor=white)](https://github.com/Tenstagram)


## ✨ 주요 기능
- **회원 관리**: 회원가입, 로그인, 로그아웃, 회원 탈퇴, 회원 정보 수정, 회원 조회
- **인증 및 권한 관리**: JWT 기반 로그인, 인증 필터 및 리졸버 적용, 인터셉터 적용
- **가게 관리**: 가게 생성, 조회, 수정, 삭제
- **메뉴 관리**: 메뉴 생성, 조회, 수정, 삭제
- **장바구니 관리**: 장바구니 생성, 조회, 삭제
- **주문 관리**: 주문 생성, 주문 상태 변경
- **리뷰 관리**: 리뷰 생성, 조회, 삭제
- **댓글 관리**: 댓글 생성, 삭제
- **상태 로깅**: 주문 상태에 따른 로깅

## 🛠️ 기술 스택
### **언어 및 프레임워크**
- Java 17  
- Spring Boot 3.4.3  

### **데이터베이스**
- MySQL  
- H2  

### **인증 및 보안**
- JWT  
- Bcrypt  

### **API 문서화**
- Swagger

### **기타**
- JPA  
- AOP  
- JUnit 5

## 📆 개발 기간
2025-02-28 ~ 2025-03-07

## 📂 프로젝트 구조

```plaintext
src
└── main
    ├── java
    │   └── xyz.tomorrowlearncamp.outsourcing
    │       ├── domain                # 도메인별 엔티티 및 관련 로직
    │       │   ├── auth               # 인증 및 사용자 인증 관련 기능
    │       │   ├── cart               # 장바구니 도메인
    │       │   ├── comment            # 댓글 도메인
    │       │   ├── menu               # 메뉴 도메인
    │       │   ├── order              # 주문 도메인
    │       │   ├── review             # 리뷰 도메인
    │       │   ├── store              # 가게 도메인
    │       │   ├── user               # 사용자 도메인
    │       ├── global                 # 프로젝트 전역에서 사용되는 모듈
    │       │   ├── config             # 설정 관련 파일
    │       │   │   ├── aop            # AOP 관련 설정
    │       │   │   ├── resolver       # 커스텀 리졸버 관련 설정
    │       │   ├── entity             # 공통 엔티티
    │       │   ├── etc                # 기타 공통 기능
    │       │   ├── exception          # 예외 처리 관련 클래스
    │       │   ├── filter             # 필터 관련 기능
    │       │   ├── interceptor        # 인터셉터 관련 기능
    │       │   ├── util               # 유틸리티 클래스
    │       └── OutsourcingApplication # Spring Boot 메인 애플리케이션
    ├── resources
    │   ├── .env.local                 # 환경 변수 설정 파일
    │   └── application.yml             # Spring Boot 설정 파일
```

## 📦 Commit Convention

### 커밋 타입 정의

| 타입      | 설명 |
|----------|----------------------------|
| feat     | 새로운 기능 추가 |
| fix      | 버그 수정 |
| refactor | 코드 리팩토링 (기능 변화 없음) |
| style    | 코드 스타일 수정 (세미콜론, 포맷팅 등) |
| docs     | 문서 추가/수정 |
| test     | 테스트 코드 추가/수정 |
| chore    | 빌드, 패키지 매니저 설정 변경 |
| pref     | 성능 개선 |
| ci       | CI/CD 관련 설정 변경 |
| build    | 빌드 시스템 수정 (Gradle, Maven 등) |

### 우리팀 커밋 컨벤션 

```
Type (도메인)! : 제목

(띄어쓰기) 제목
(띄어쓰기) 내용1
(띄어쓰기) 내용2
(띄어쓰기) 내용3

꼬리말: 상태
```

## 📦 Code Convention

### 파일 및 클래스
- **파일**: `(기능)(도메인)(파일 속성)` 형식 사용 
- **클래스**: PascalCase 사용 (명확한 의미의 이름)  
- **컨트롤러**: `(기능)(도메인)Controller`
- **서비스**: `(기능)(도메인)Service`
- **레포지토리**: `(도메인)Repository`
- **엔티티**: `(도메인)Entity`
  - `BaseEntity`를 상속받아 생성일/수정일 포함

### DTO
- **Request DTO**: `(기능)(엔티티)RequestDto`
- **Response DTO**: `(기능)(엔티티)ResponseDto`

### 메서드
- **일반 메서드**: `(기능)(도메인)(속성)`
- **Getter 메서드**:
  - 단일 객체: `getUser()`
  - 리스트/복수 데이터: `getUsers()`
- **Boolean 값 반환 메서드**:
  - 객체 상태 확인: `isXX()`
  - 특정 속성 존재 여부 확인: `hasXX()`


## 🖼️ 와이어프레임
[와이어 프레임 보기](https://docs.google.com/presentation/d/1yCuBpwfNfkZYYNE7fFd8EtlfCu1EpVwg-mtrNaY7Uys/edit#slide=id.p)

## 💡 ERD
![Image](https://github.com/user-attachments/assets/efc2fad7-74af-4981-9d2a-8d694bea1d52)

## 📖 API 명세서
자세한 API 명세는 API 문서를 통해 확인할 수 있습니다.

[API 명세 보기](https://www.notion.so/teamsparta/1a82dc3ef514809b89dbd4586d39f48e?v=1a82dc3ef51481668514000c1455a4bf)

## 📖 요구사항 명세서
자세한 요구사항 명세는 아래 링크를 통해 확인할 수 있습니다.

[요구사항 명세서 보기](https://www.notion.so/teamsparta/08ae28e715d34094996c62f6d0d4fbfc?v=9270c2a8580e4cadb05e5086e85b7a20)

## 📖 기능 명세서
자세한 기능 명세는 아래 링크를 통해 확인할 수 있습니다.

[기능 명세서 보기](https://www.notion.so/teamsparta/1a82dc3ef514807894b6fbfbba2097f9?v=15a12966249d49f48cc8b65d59faef1f)

## 📺 시연 영상
프로젝트 시연 영상은 영상 링크를 통해 확인할 수 있습니다.

[시연 영상 보기](https://www.notion.so/teamsparta/1a92dc3ef51480c2a439fc12b208de91)

## 👤 팀원 소개

<table>
  <tr>
    <td align="center">
      <img src="https://avatars.githubusercontent.com/u/109287950?v=4" width="100px;"/><br />
      <b>이은성</b><br />
      리뷰 및 댓글 <br />
      <a href="https://github.com/mixedsider">GitHub</a>
    </td>
    <td align="center">
      <img src="https://avatars.githubusercontent.com/u/192684313?v=4" width="100px;"/><br />
      <b>명민준</b><br />
      메뉴 <br />
      <a href="https://github.com/mmj-159">GitHub</a>
    </td>
    <td align="center">
      <img src="https://avatars.githubusercontent.com/u/86907076?v=4" width="100px;"/><br />
      <b>이채원</b><br />
      장바구니 및 주문 <br />
      <a href="https://github.com/3uomlkh">GitHub</a>
    </td>
    <td align="center">
      <img src="https://avatars.githubusercontent.com/u/144771457?v=4" width="100px;"/><br />
      <b>유명훈</b><br />
      인증/인가 <br />
      <a href="https://github.com/mhoo999">GitHub</a>
    </td>
    <td align="center">
      <img src="https://avatars.githubusercontent.com/u/103635792?v=4" width="100px;"/><br />
      <b>한윤희</b><br />
      가게 <br />
      <a href="https://github.com/Iamhamster">GitHub</a>
    </td>
  </tr>
</table>

