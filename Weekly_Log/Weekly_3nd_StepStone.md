## 팀 구성원, 개인 별 역할


조연준 - 채팅 예제 구현

곽동욱 - Post Domain 구현

김은정 - User Domain 구현

이지현 - Like Domain 구현

편재원 - SpringSecurity 구현

## 팀 내부 회의 진행 회차 및 일자


1회차(2022.08.15) 디스코드 회의 진행, 전원 참석

2회차(2022.08.16) 디스코드 회의 진행, 전원 참석

3회차(2022.08.17) 디스코드 회의 진행, 전원 참석

4회차(2022.08.18) 디스코드 회의 진행, 전원 참석

5회차(2022.08.19) 디스코드 회의 진행, 전원 참석

## 현재까지 개발 과정 요약 (최소 500자 이상)


- CRUD, 채팅 예제 구현 완료

- ERD 수정


  8월 17일
  ![image](https://user-images.githubusercontent.com/47443884/185881429-3a994f44-0f48-4629-8fb4-16a60ba36e1b.png)
  
    8월 18일
  ![image](https://user-images.githubusercontent.com/47443884/185881689-b68177e6-9677-4d04-8ab1-175ba35bec54.png)

- 각자 CRUD Dev 브랜치 머지, 충돌 해결


- 변경 사항
    
    ERD 에 채팅관련 테이블 추가
    
    user 테이블 - user_id, password `UUID` → `String`형 변환
    
    - encoder 값을 읽어올 수 있기 위해서는 user id 값, password 값이 무조건 string이 되어야 한다.
    - DB에 저장된 값으로 비교를 해야하는데
    UUID 가 해쉬 값이 아니라서 String으로 바꿔야할 것 같다.

- 각 브랜치 dev 브랜치로 Merge 진행함
    
    [Commits · likelion-backendschool/step-stone (github.com)](https://github.com/likelion-backendschool/step-stone/commits/dev)
    
- WBS 정리
    
    [6팀 BES HACKATHON WBS](https://docs.google.com/spreadsheets/d/1X-_-lXIVWIaiEIx_dEUlg7xBh-RDNaCbqwH0ymMxqag/edit#gid=0)
    

## 개발 과정에서 나왔던 질문 (최소 200자 이상)


오류 → 해결

- user 테이블 - user_id, password `UUID` → `String`형 변환 과정 진행 후 delete 실행 시 오류 발생
    - No EntityManager with actual transaction available for current thread - cannot reliably process 'remove' call
- `@Transactional` 어노테이션 추가로 해결
- 이유 : 내부적으로 쌓인 캐시를 `delete`를 했을 때 다 비워줘야 하는것과 얽혀있을 것 같은데 자세한 이유는 다음에 강사님이 찾아보시고 알려주신다 하셨습니다.

UUID와 increment PK 사용 경우

[UUID와 increment PK는 언제 사용해야할까?](https://americanopeople.tistory.com/378)

- Optional 에서 ispresent를 하지 않고, get()을 바로 실행해서 발생한 문제

```Java
  //수정 전 코드
public void idCheck(UUID postId, UUID userId){
		LikeEntity likes =likeRepository.findByUserIdAndPostId(postId, userId).get();   
	  if (likes == null) {  
			 like(postId, userId);  
//null 처리 후에 get(); 을 해야 하는데 null 처리 전에 get을 해서 No value present 발생(?)

>> 수정

public void idCheck2(UUID postId, UUID userId){
       Optional<LikeEntity> likes = likeRepository.findByPostIdAndUserId(postId, userId); 
       if(likes.isPresent()){
           deletelikes(postId, userId);
// Optional로 받은 값은 isPresent()를 통해서 값 확인
  ```
