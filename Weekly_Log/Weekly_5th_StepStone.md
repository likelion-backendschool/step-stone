## 팀 구성원, 개인 별 역할


조연준 - 채팅 예제 구현

곽동욱 - Post Domain 구현

김은정 - User Domain 구현, UI 디자인

이지현 - Like , workspace Domain 구현

편재원 - SpringSecurity 구현

각자 파트에 맞는 UI 구현

## 팀 내부 회의 진행 회차 및 일자


1회차(2022.08.29) 디스코드 회의 진행, 전원 참석

2회차(2022.08.30) 디스코드 회의 진행, 전원 참석

3회차(2022.08.31) 디스코드 회의 진행, 전원 참석

4회차(2022.09.01) 디스코드 회의 진행, 전원 참석

5회차(2022.09.02) 디스코드 회의 진행, 전원 참석

## 현재까지 개발 과정 요약 (최소 500자 이상)


- 중간점검 피드백 - 성진 멘토님
    - 기능 구현 이후 추가적으로 UI 부분 더 예쁘게 구현하셔도 좋을 것 같다.
    - 남은 시간 동안 구현 잘 마무리 짓고 완성도 높이기

- 2차 코드 머지

![image](https://user-images.githubusercontent.com/47443884/188520034-86e4511a-1cdc-4860-a5fe-e15d09221aad.png)

- 채팅
    - 채팅방 UI (진행률 90%)
    - 초대하기 기능 구현

![image](https://user-images.githubusercontent.com/47443884/188520043-caf30680-d04f-4139-94a4-e6cad47e77e9.png)

- 메인 페이지 - `최근 이슈 게시글` 구현

![image](https://user-images.githubusercontent.com/47443884/188520071-82b7aed7-67c9-4997-81e6-a6c4e7f9f712.png)

`likes` 수 순으로 정렬 → 3개씩 carousel에 담아서 최대 9개의 게시글까지 각 card에 담아서 표현

- 마이페이지 구현 - UI 뼈대 완료 / 기능 구현 진행 중

![image](https://user-images.githubusercontent.com/47443884/188520078-0ab14d83-53e2-4315-8409-d70883606ca0.png)

- 로그인/로그아웃
    - OAuth2를 이용한 로그인/로그아웃 구현
    - JWT를 이용해 인증/인가 처리
    - UI 뼈대 완료

![image](https://user-images.githubusercontent.com/47443884/188520082-252ce5cb-22ed-41f0-94a4-90d2f58ce4ab.png)

- 게시글 detail페이지 줄바꿈 적용

![image](https://user-images.githubusercontent.com/47443884/188520091-fff67dc3-523d-4d54-a036-a293132ff438.png)

- WBS 정리
    
    [6팀 BES HACKATHON WBS](https://docs.google.com/spreadsheets/d/1X-_-lXIVWIaiEIx_dEUlg7xBh-RDNaCbqwH0ymMxqag/edit#gid=0)
    

## 개발 과정에서 나왔던 질문 (최소 200자 이상)


- 최근 이슈 게시글 수정 (진행중)
    - 이중 리스트 사용하는 방향으로
- `<div *layout:fragment*="content">` 를 안붙이면 화면 나타나지 않음
    - 사용하는 js 코드들도 모두 태그 안에 포함시켜야 함.
    
- `card_carousel_style.css`파일 전체 layout 파일에 적용되어 있었어서 모든 `card` class들의 스타일이 이상했던 부분 해결
- 작성 form 에 row 제거 → 쏠림 현상 해결
