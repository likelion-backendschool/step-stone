## 팀 구성원, 개인 별 역할


조연준 - 채팅 예제 구현

곽동욱 - Post Domain 구현

김은정 - User Domain 구현, UI 디자인

이지현 - Like , workspace Domain 구현

편재원 - SpringSecurity 구현

각자 파트에 맞는 UI 구현

## 팀 내부 회의 진행 회차 및 일자


1회차(2022.09.05) 디스코드 회의 진행, 전원 참석

2회차(2022.09.06) 디스코드 회의 진행, 전원 참석

2회차(2022.09.07) 디스코드 회의 진행, 전원 참석

## 현재까지 개발 과정 요약 (최소 500자 이상)


- 2차 중간점검 피드백 - 성진 멘토님
    - 그래도 완성도가 높으신거 같아서 남은 기간 동안 게시글 좋아요 기능 및 자잘한 버그 수정만 확실하게 해주시면 될 것 같습니다.

- 마이페이지 기능 구현
    1. User Role 표시
        
        ![image](https://user-images.githubusercontent.com/47443884/189860255-94fc4ef5-5c97-47ca-93db-7930e8cff69e.png)
        
        ![image](https://user-images.githubusercontent.com/47443884/189860306-387cd6ee-afb9-42d0-8d8f-f75ab5a0497f.png)
        
    2. 비밀번호 변경 기능
        
        ![image](https://user-images.githubusercontent.com/47443884/189860335-a805992c-337e-473d-9ed6-1eda740f5b31.png)
        
    3. User Role 에 따른 게시글 리스트 표현 → 페이징 처리 필요
        
        ![image](https://user-images.githubusercontent.com/47443884/189860350-6abc15e3-bee9-4c24-8bad-8e1436b92782.png)
        
        ![image](https://user-images.githubusercontent.com/47443884/189860390-a76d3301-17f0-4590-878f-531b0b3821eb.png)
        

- 알림 UI 구현
    
    ![image](https://user-images.githubusercontent.com/47443884/189860428-189cfd04-86ee-4f1d-887a-d77f48295873.png)
    
- JWT를 쿠키로 저장하는 과정에서 csrf 취약점이 발견 되어 sameSite = Strict 속성을 추가함.
    
- WBS
   [6팀 BES HACKATHON WBS](https://docs.google.com/spreadsheets/d/1X-_-lXIVWIaiEIx_dEUlg7xBh-RDNaCbqwH0ymMxqag/edit#gid=0)

## 개발 과정에서 나왔던 질문 (최소 200자 이상)


- 부트스트랩 버전 충돌에 대한 버그
    - bootstrap 5.2 버전을 사용하면 5.2.1 기능이 제대로 작동하지 않음
    
- 게시글 수정/삭제 PostEntity PostDto 문제
    
    ```java
    // Contoroller의 Entity를 Dto로 변경하면서 Dto->Entity로 변환
    
    public void delete(PostDto postDto) {
            PostEntity postEntity = PostEntity.toEntity(postDto);
            postRepository.delete(postEntity);}
    
        public void modify(PostDto postDto, String title, String body) {
            postDto.setTitle(title);
            postDto.setBody(body);
            postDto.setUpdatedAt(LocalDateTime.now());
            PostEntity postEntity = PostEntity.toEntity(postDto);
    
            postRepository.save(postEntity);
        }
    
    /*  변경 후 수정하면 수정이 아니라 새로운 게시글이 작성되고 삭제는 안 되는 버그 발생
    Dto->Entity로 변경 후 저장하는게 아닌 Dto.getPostCid로 Entity를 구한 후 저장으로 변경*/
    
    public void delete(PostDto postDto) {
            PostEntity postEntity = postRepository.findByPostCid(postDto.getPostCid());
    
            postRepository.delete(postEntity);}
    
        public void modify(PostDto postDto, String title, String body) {
            PostEntity postEntity = postRepository.findByPostCid(postDto.getPostCid());
    
            postEntity.setTitle(title);
            postEntity.setBody(body);
            postEntity.setUpdatedAt(LocalDateTime.now());
    
            postRepository.save(postEntity);
        }
    ```
