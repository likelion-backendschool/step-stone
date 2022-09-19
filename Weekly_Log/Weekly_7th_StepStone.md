## 팀 구성원, 개인 별 역할


조연준 - 채팅 예제 구현

곽동욱 - Post Domain 구현

김은정 - User Domain 구현, UI 디자인

이지현 - Like , workspace Domain 구현

편재원 - SpringSecurity 구현

각자 파트에 맞는 UI 구현

## 팀 내부 회의 진행 회차 및 일자


1회차(2022.09.12) 디스코드 회의 진행, 전원 참석

2회차(2022.09.13) 디스코드 회의 진행, 전원 참석

3회차(2022.09.14) 디스코드 회의 진행, 전원 참석

4회차(2022.09.15) 디스코드 회의 진행, 전원 참석

5회차(2022.09.16) 디스코드 회의 진행, 전원 참석

## 현재까지 개발 과정 요약 (최소 500자 이상)


- 메인화면 UI 수정
    - 구성 추가, 최근이슈게시글 카드 UI 수정, font 변경
    
    ![image](https://user-images.githubusercontent.com/47443884/190980482-da910ab4-64a9-428a-84c2-8ba164010f3c.png)
    

- 자잘한 UI 수정
    - 페이지네이션 아래 페이징 부분 색 변경
    - font 크기 조정
    - 로그인 화면 카드 크기 조정 / 회원가입 화면 미세한 텍스트 간격 조정
    - 푸터 내용 수정
    - 게시글 목록 상단에 최근 이슈 게시글 부분 추가

- 마이페이지 - 비밀번호 변경 폼
    - 비밀번호 변경 시, 길이 제한 설정 (8글자 이상, 25글자 미만)

- 마이페이지 - 비밀번호 변경 버튼 조건 추가
    - 마이페이지에서 Oauth 로그인 유저는 비밀번호 변경 접근 불가 설정

- 새로운 채팅 알림 구현
    - 채팅 JS 파일 분리
    - 채팅 화면에 있을 때는 알림 발송 안하도록 구현
    - 이벤트 리스너 사용

- Git 머지 진행 완료

- 인가 구현
    - 권한에 따라 기능 제한

- 패스워드 등 Validation 기능 추가

- 게시글 끌올 기능 구현
    - 게시글 작성 후 5일 이후부터 끌올 가능하도록 조건 설정

- 로그인한 user 정보 받아오는 방법 변경
    - principal → AuthenticationPrincipal PrincipalDetails 로 변경

- WBS 정리
    
    [6팀 BES HACKATHON WBS](https://docs.google.com/spreadsheets/d/1X-_-lXIVWIaiEIx_dEUlg7xBh-RDNaCbqwH0ymMxqag/edit#gid=0)
    

## 개발 과정에서 나왔던 질문 (최소 200자 이상)


- merge 이후 like 기능 에러
    - DB에서 like 테이블 삭제 후 재실행으로 해결

- Token Expired 문제 해결
    - 토큰만료시간과 쿠키만료시간에 차이가 존재 → 해당 차이를 없앰

- 비로그인 상태일 때  if( principalDetails == null ) 함수를 타지 않는 이유
    - SpringSecurity가 비로그인 상태일 경우 바로 login페이지로 이동하게 설정되어 있음
