## 팀 구성원, 개인 별 역할

조연준 

곽동욱

김은정

이지현

편재원

전원 CRUD 및 UI 공동 개발 참여

## 팀 내부 회의 진행 회차 및 일자

1회차(2022.08.01) 디스코드 회의 진행, 전원 참석

2회차(2022.08.02) 디스코드 회의 진행, 전원 참석

3회차(2022.08.03) 디스코드 회의 진행, 전원 참석

4회차(2022.08.04) 디스코드 회의 진행, 전원 참석

5회차(2022.08.05) 디스코드 회의 진행, 곽동욱님 불참

## 현재까지 개발 과정 요약 (최소 500자 이상)

- MariaDB ERD 설계
    
    Entity Relationship Diagram을 [diagrams.net](http://diagrams.net) 내에서 설계, 게시글과 관련된 Post, 사용자와 관련된 User, 좋아요 기능과 관련된 Likes 테이블을 구축하기로 하고, 내부 Column들을 설계
    
    ![image](https://user-images.githubusercontent.com/47443884/183424265-36c57d67-b68b-4e18-bef3-646a460cf4a8.png)

<br>
    
- Spring 프로젝트 생성 및 MVC 패턴 적용
    
    Spring Initailizr를 통해 Spring 프로젝트를 생성하고, Controller, Service, respository 클래스를 선언하여 MVC 패턴 frame 작업 진행
    
    <br>
    
- Entity, Dto, Vo 모델 클래스 생성
    
    DB에 직접 접근하는 Entity, Layer간의 Data 전송을 담당하는 Dto, 값을 조회하는 Vo 모델 클래스로 각 모델의 역할 분담
    
    <br>
    
- 커밋 Convention 수립
    - [[Git] git 커밋 컨벤션 (Git Commit Message Convention) (tistory.com)](https://aroundlena.tistory.com/55?category=740019)
    - [[Git] Commit Message Convention (velog.io)](https://velog.io/@archivvonjang/Git-Commit-Message-Convention)
        
        위 두 링크를 참조하여 커밋 Convention 수립
        
        <br>
        
- 코드 네이밍 규칙 수립
    - **Class** - PascalCase
    - **Field** - camelCase
    - **Method** - camelCase
    - **Package** - lowercase
    - **DB table** - snake_case
    - **DB Attribute** - snake_case
    
    코드 네이밍 규칙을 다음과 같이 협의
    
    <br>
    
- 게시판 기능 어떤 화면이 필요할지 회의
    - 게시판 작성
    - 게시판 목록
        
        위 두 화면에 맞게 CRUD 구축 계획
        
        <br>
    
- 게시글 CRUD 구현 시작
    
    <br>
    
- 로컬 DB 구축
    
    [stepstone_dev(순서만 바꾼 수정본).sql](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/36876e57-b330-4f13-9a67-8a8e7f3a8da0/stepstone_dev(%EC%88%9C%EC%84%9C%EB%A7%8C_%EB%B0%94%EA%BE%BC_%EC%88%98%EC%A0%95%EB%B3%B8).sql)
    
    로컬에서 DB를 구축하고, SQL문으로 각자 로컬에서 DB로 구축
    
    <br>
    
- Figma UI 설계 진행 - 은정님이 도맡아 디자인
    
    [스탭스톤 UI 설계](https://www.figma.com/file/CQ2BShKTDnCf10nlxMRsBb/%EC%8A%A4%ED%83%AD%EC%8A%A4%ED%86%A4-UI-%EC%84%A4%EA%B3%84?node-id=0%3A1)
    
    <br>
    

## 개발 과정에서 나왔던 질문 (최소 200자 이상)

- Git branch를 어떻게 하는지?
- GIT 클론을 해서 새로운 브랜치를 어떻게 만들고, DB를 어떻게 연동하고. CRUD를 어떻게 구현해야하고, @RequestMapping, @DeleteMapping 등 여러 Mapping의 차이점은 무엇인가요.
    
    [https://itworldyo.tistory.com/32](https://itworldyo.tistory.com/32)
    
- Sourcetree 에서 저장소 복제가 되지 않아요 (맥)
    
    시도해본 해결 방법
    
    1. 사용자 계정 삭제하고 다시 추가하기(on macOS)
    
    [[Git] 사용자 계정 삭제하고 다시 추가하기(on macOS)](https://architectophile.tistory.com/13)
    
    1. Sourcetree 삭제 후 재설치
    2. clone 다시해서 로컬저장소로 추가
    
    해당 방법들로 해결하였습니다.
    
    <br>

## 개발 결과물 공유
![image](https://user-images.githubusercontent.com/47443884/183425161-2eac0394-d3f8-4e1f-9f55-48c703b19670.png)

