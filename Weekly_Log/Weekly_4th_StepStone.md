## 팀 구성원, 개인 별 역할


조연준 - 채팅 예제 구현

곽동욱 - Post Domain 구현

김은정 - User Domain 구현, UI 디자인

이지현 - Like , workspace Domain 구현

편재원 - SpringSecurity 구현

각자 파트에 맞는 UI 구현

## 팀 내부 회의 진행 회차 및 일자


1회차(2022.08.22) 디스코드 회의 진행, 전원 참석

2회차(2022.08.23) 디스코드 회의 진행, 전원 참석

3회차(2022.08.24) 디스코드 회의 진행, 전원 참석

4회차(2022.08.25) 디스코드 회의 진행, 전원 참석

5회차(2022.08.26) 디스코드 회의 진행, 전원 참석

## 현재까지 개발 과정 요약 (최소 500자 이상)


- ERD 재수정

![Untitled](![image](https://user-images.githubusercontent.com/47443884/187329320-75a92dc1-626c-479b-a9be-c6f75132dd8f.png)

- 채팅
    - chat chatroom CRUD 구현 완료
    - Stomp, ajax 사용하여 채팅 기능 구현
    
    ![Untitled](![image](https://user-images.githubusercontent.com/47443884/187329356-19d194f5-208d-449f-b732-d7036bd4d11e.png)
    

- 전제적인 UI 구현 완료
    
    ![Untitled](![image](https://user-images.githubusercontent.com/47443884/187329380-1bff2fb9-92bc-417a-a8b7-d114afcd7e7c.png)
    

- 프로젝트 진행 내용
    
    ![스크린샷 2022-08-29 오후 5.32.36.png](![image](https://user-images.githubusercontent.com/47443884/187329429-3f1d7beb-e651-4f70-97ab-e128da8ccfa0.png)
    

- 메인 페이지 UI 구현
    - 헤더 + 푸터 (navbar) / layout 적용
    
    ![스크린샷 2022-08-29 오후 5.36.13.png](![image](https://user-images.githubusercontent.com/47443884/187329470-eb43c263-890b-4c89-8220-8f6ffb5a282b.png)
    
    ![스크린샷 2022-08-29 오후 5.36.46.png](![image](https://user-images.githubusercontent.com/47443884/187329490-619cf87e-587d-4e39-a37a-92ea15e7d0fd.png)
    

- WBS 정리
    
    [6팀 BES HACKATHON WBS](https://docs.google.com/spreadsheets/d/1X-_-lXIVWIaiEIx_dEUlg7xBh-RDNaCbqwH0ymMxqag/edit#gid=0)
    

## 개발 과정에서 나왔던 질문 (최소 200자 이상)


1. 개발자 모집을 어떻게 할 것인가 - 워크스페이스에서 대표 개발자가 게시글 작성으로 모집
    1. 1안. 모집 게시판에서 알아서 하도록
    
    게시글에 `모집중` , `모집완료` 표시 (status)
    
    **- 게시글에 써야할 내용**
    
    - 어떤 게시글을 프로젝트로 시작하는지
    - 모집안내글
        - 필요한 개발자 직군
        - 예상 개발 일정
        - (글쓴이?) 대표 연락처 및 정보
        - 참여를 위해 연락할 수 있는 링크 (오픈채팅방 링크 등)
    
2. 채팅방 기능이 구체적으로 어떻게 사용할 것인가
    1. 게시글 작성자와 연결한다.
    2. 프로젝트 참여 전체 인원이 채팅에 참여한다.
    
3. 마이페이지 구성 요소
    1. 이름
    2. 비밀번호 재설정
    3. ~~진행중인 프로젝트 → 개발자 전용~~
    4. 작성한 게시글
    5. 채팅방 이동

1. 프로젝트 완료 페이지 추가 (헤더에도 추가하기)
    1. 앱스토어 링크 등을 담은 게시글을 올리는 공간
    
    **- 게시글에 써야할 내용**
    
    - 어떤 게시글을 프로젝트로 시작했는지
    - 완료된 프로젝트 링크
    - 참여한 개발자들
    - 추가 설명
