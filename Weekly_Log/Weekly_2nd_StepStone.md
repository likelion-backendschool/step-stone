## 팀 구성원, 개인 별 역할


조연준 - 채팅 구현

곽동욱 - Post Domain 구현

김은정 - User Domain 구현

이지현 - Like Domain 구현

편재원 - SpringSecurity 구현

<br>

## 팀 내부 회의 진행 회차 및 일자



1회차(2022.08.08) 디스코드 회의 진행, 전원 참석

2회차(2022.08.09) 디스코드 회의 진행, 전원 참석

3회차(2022.08.10) 디스코드 회의 진행, 전원 참석

4회차(2022.08.11) 디스코드 회의 진행, 전원 참석

5회차(2022.08.12) 디스코드 회의 진행, 전원 참석

<br>

## 현재까지 개발 과정 요약 (최소 500자 이상)



- 타임 리프 예제 학습
    
    [Tutorial: Thymeleaf + Spring](https://www.thymeleaf.org/doc/tutorials/3.0/thymeleafspring.html)
    
    [[Spring Boot] 타임리프(Thymeleaf)란? : 네이버 블로그 (naver.com)](https://blog.naver.com/PostView.nhn?isHttpsRedirect=true&blogId=bgpoilkj&logNo=221982228705&parentCategoryNo=20&categoryNo=&viewDate=&isShowPopularPosts=false&from=postView)
    
    두 가지 Ref 참고하여 Thymeleaf 사용법 학습 
    
    <br>
    
- Github Flow Pull Request 학습
    
    [Github flow 사용법 & 시나리오](https://velog.io/@taeate/Github-flow-%EC%82%AC%EC%9A%A9%EB%B2%95-%EC%8B%9C%EB%82%98%EB%A6%AC%EC%98%A4)
    
    멘토님이 정리해주신 Github flow 사용법 참고하여 Pull Request 사용하기로 결정
    
    <br>
    
- 개발 담당 도메인 분배
    - 기존에는 Post 테이블에 대한 CRUD 파트를 각자 나눠서 구현하고자 했지만, 테이블을 하나씩 도맡아서 각 테이블 당의 CRUD를 구현하는 것으로 계획 변경
    - 목표 : 각자 이번주 금요일(12일)까지 CRUD 구현
    - Spring Security는 JWT 기능을 사용하고 OAuth 기능을 고려 하면 논의 중
    - 채팅 구현은 하단 링크를 참고하여
        
        [Spring websocket chatting server(1) - basic websocket server](https://daddyprogrammer.org/post/4077/spring-websocket-chatting/)
        
<br>

- Post Create 예제
    
    [https://github.com/likelion-backendschool/step-stone/commits/dev](https://github.com/likelion-backendschool/step-stone/commits/dev)
    
    PostController에서 실제 Post를 Create 하여 DB에 저장하는 과정을 팀원들과 공유하여, 작업의 방향성을 공유함.
    
    <br>
    
- Spring Config Injection 예제
    
    [https://github.com/ChoYeonJun/SpringConfigInjection](https://github.com/ChoYeonJun/SpringConfigInjection)
    
    @Autowired annotation을 사용하는 것이 아닌 SpringConfig 파일에서 Service, Repository를 @Bean으로 등록하여 injection 방향으로 구현하기로 정함.
    
    <br>
    
- 오류 해결
    
    하단 질문에 상세 설명 기입
    
    <br>
    

- WBS 정리
    
    [6팀 BES HACKATHON WBS](https://docs.google.com/spreadsheets/d/1X-_-lXIVWIaiEIx_dEUlg7xBh-RDNaCbqwH0ymMxqag/edit#gid=0)
    
    <br>
    

## 개발 과정에서 나왔던 질문 (최소 200자 이상)



- 동욱님 에러 발생 : Failed to create query for method public abstract void com.likelion.stepstone.post.PostRepository.findById(java.util.UUID)! No property 'id' found for type 'PostEntity';
    
    JPA Repository naming 규칙을 맞추어 interface 내에서 DB에 접근하는 메소드 선언이 필요함.
    
    [](https://www.baeldung.com/spring-data-jpa-custom-naming)
    
    <br>
    
- 은정님 에러 발생 : Post 테이블 속 likes 값 + User 테이블 속 created_at 값에 대한 오류 발생
    - Post 테이블에  likes 값이 `DEFAULT 0` 으로 지정되어 있지만 `postEntity.setLikes(0);` 을 해주지 않으면 `NULL` 값이 들어가는 오류 발생
    - User 테이블 CRUD 진행 중, `created_at` 에 `DEFAULT`값이 있음에도 `Null`이 들어올 수 없다는 오류 발생
    
    [How can you make a created_at column generate the creation date-time automatically like an ID automatically gets created?](https://stackoverflow.com/questions/49954812/how-can-you-make-a-created-at-column-generate-the-creation-date-time-automatical)
    
    [Column cannot be null Mysql](https://stackoverflow.com/questions/37698167/column-cannot-be-null-mysql)
    
    [DB) NOT NULL과 DEFAULT를 동시에 쓸 필요가 있는가?](https://frhyme.github.io/others/DB_NOT_NULL_vs_default/)
    
    <br>
