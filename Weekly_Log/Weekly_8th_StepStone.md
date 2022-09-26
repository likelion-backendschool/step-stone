## 팀 구성원, 개인 별 역할


조연준 - 채팅 예제 구현

김은정 - User Domain 구현, UI 디자인

이지현 - Like , workspace Domain 구현

편재원 - SpringSecurity 구현

각자 파트에 맞는 UI 구현

## 팀 내부 회의 진행 회차 및 일자


1회차(2022.09.19) 디스코드 회의 진행, 전원 참석

2회차(2022.09.20) 디스코드 회의 진행, 전원 참석

3회차(2022.09.21) 디스코드 회의 진행, 전원 참석

4회차(2022.09.22) 디스코드 회의 진행, 전원 참석

5회차(2022.09.23) 디스코드 회의 진행, 전원 참석 

## 현재까지 개발 과정 요약 (최소 500자 이상)


- 권한 설정 추가 (게시글 권한, 채팅방 권한)
- 알림 UI 수정
- 이미지 파일들 폴더 별로 정리 → 경로 수정

- 마이페이지 - 개발자일 때 완성된 프로젝트 리스트, 내가 좋아요 누른 게시글 리스트 추가

![image](https://user-images.githubusercontent.com/47443884/192233278-06aded8d-edaf-4ec9-ad43-a67b326a3589.png)

- 채팅방 UI 수정

![image](https://user-images.githubusercontent.com/47443884/192233300-15821349-a039-45f5-b0b7-4dd0dccddfc2.png)

- 문의하기, 채팅방 초대, 새 채팅 알림 생성
- 알림 animation 추가

- Validation 처리
    - 비밀번호 validation
        
        ![image](https://user-images.githubusercontent.com/47443884/192233318-05cf8a9b-6f19-4000-8314-2c6ffee6997b.png)
        
    - ID가 중복일 시
        
        ![image](https://user-images.githubusercontent.com/47443884/192233336-514306d5-5e2a-428a-b3ee-4d35d4203539.png)
        

- 다른 페이지로 연동되는 버튼 추가
    
    ![image](https://user-images.githubusercontent.com/47443884/192233360-aa6db8b1-ec8f-4b4f-8f31-7a3febdd6e13.png)
    

- WBS 정리
    
    [6팀 BES HACKATHON WBS](https://docs.google.com/spreadsheets/d/1X-_-lXIVWIaiEIx_dEUlg7xBh-RDNaCbqwH0ymMxqag/edit#gid=0)
    

- 최종점검 강사님 피드백 요약
    
    > Open graph
    구글 애널리틱스
    구글 사이트 등록, 네이버 사이트 등록
    반응형
    디자인
    > 

## 개발 과정에서 나왔던 질문 (최소 200자 이상)


- 게시글 리스트에 게시글이 0개면 화면이 나오지 않는 오류 해결
    - 빠진 else구문 추가로 해결
- modify 오류 수정
- modal 버튼에 타임리프 변수를 넣어도 최상위 값만 인식되는 오류 해결
    
    ```html
    <script th:inline="javascript">
        /*<![CDATA[*/
        function SetParamModal(modal_id) {
            $('#modal_id').val(modal_id);
        }
    
        function work() {
            id = $('#modal_id').val();
            location.href = "/workspace/list/" + id;
        }
    
        function project() {
            id = $('#modal_id').val();
            location.href = "/project/list/" + id;
        }
    
        /*]]>*/
    </script>
    
       <input type="hidden" name="modal_id" id="modal_id">
       <button type="button" class="post1" data-toggle="modal" data-bs-toggle="modal" data-bs-target="#staticBackdrop5" 
       data-target="#Delete_Modal" th:onclick="SetParamModal([[${post.postCid}]]);"></button>
       //  SetParamModal() 함수를 통해서 타임리프 변수를 넘겨주기 
      
        <div class="modal-footer">
           <button type="button" class="btn btn-primary" href="#" onclick="work()"> </button>
    ```
