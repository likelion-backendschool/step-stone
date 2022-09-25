package com.likelion.stepstone.stepstone.post;

import com.likelion.stepstone.post.PostRepository;
import com.likelion.stepstone.post.model.PostEntity;
import com.likelion.stepstone.projects.ProjectRepository;
import com.likelion.stepstone.projects.model.ProjectEntity;
import com.likelion.stepstone.user.UserRepository;
import com.likelion.stepstone.user.model.UserEntity;
import com.likelion.stepstone.workspaces.WorkSpaceRepository;
import com.likelion.stepstone.workspaces.model.WorkSpaceEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDateTime;


@SpringBootTest
public class PostControllerTest {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    WorkSpaceRepository workspaceRepository;
    @Autowired
    ProjectRepository projectRepository;

    //페이징처리 위한 아무말
    @Test
    void addTestPost() {

        UserEntity user = userRepository.findByUserId("user1").get();

        for (Long i = 1L; i <= 10L; i++) {

            String title = Long.toString(i);

            PostEntity p = new PostEntity();

            p.setTitle(title);
            p.setBody("작은 액자를 제 시선이 자주 닿는 곳에 걸어보기도 하고\n" +
                    "\n" +
                    "더 쉽게는 엽서를 벽에 마스킹 테이프로 붙여보기도 해요\n" +
                    "\n" +
                    "그러다 또 어느 날엔 매번 앉는 의자의 방향을 \n" +
                    "\n" +
                    "바꿔서 앉아보기도 하고\n" +
                    "\n" +
                    "(요고 괜찮아요 완전히 색다른 시선을 가질 수 있거든요^^)\n" +
                    "\n" +
                    "화분들의 위치를 바꿔서 초록이들을\n" +
                    "\n" +
                    "제 시선 앞에 두기도 해요\n" +
                    "\n" +
                    "\u200B\n" +
                    "\n" +
                    "늘 대단한 변화는 결코 아니에요\n" +
                    "\n" +
                    "간혹, 드물게 어느 날은  큰 움직임으로 \n" +
                    "\n" +
                    "그러하기도 하지만\n" +
                    "\n" +
                    "그 외 많은 날들은 작은 움직임만으로도\n" +
                    "\n" +
                    " 큰 기분전환이 되어 이러한 행동들이\n" +
                    "\n" +
                    "저에게 행복감을 주거든요^^\n" +
                    "[출처] 매일 조금씩 달라지는 서재방인테리어|작성자 초이스홈스타일링");
            p.setLikes(0);
            p.setUser(user);
            p.setCreatedAt(LocalDateTime.now());
            p.setUpdatedAt(LocalDateTime.now());
            postRepository.save(p);

        }
    }
        @Test
        void addTestPost2() {

            UserEntity user = userRepository.findByUserId("user1").get();

                //postCid = 11
                PostEntity p = new PostEntity();

                p.setTitle("test");
                p.setBody("아이폰에 애플페이 추가해주세요\n" +
                        "\n" +
                        "이제 곧 될거라는 말이 있는데 뭐가 진짜일까요\n" +
                        "\n" +
                        "워치로는 지금도 사용할 수 있다고 하는데  \n" +
                        "\n" +
                        "설정하기 귀찮네요\n" +
                        "\n" +
                        "사실 애플페이 도입 되더라도 별로 안 쓸 것 같고\n" +
                        "\n" +
                        "테스트데이터 멘트를 쓰려고 하는데 너무 힘드네요 \n" +
                        "\n" +
                        "좋은 아이디어 있으시면 추천해주세요");
                p.setLikes(0);
                p.setUser(user);
                p.setCreatedAt(LocalDateTime.now());
                p.setUpdatedAt(LocalDateTime.now());
                postRepository.save(p);

    }

    @Test
    void addWorkspacePost() {

        //workspaceCie = 1
        UserEntity user = userRepository.findByUserId("devel1").get();


        WorkSpaceEntity work = new WorkSpaceEntity();

        work.setTitle("애플페이 추가할사람");
        work.setBody("삼성페이 그렇게 좋은가여?\n" +
                "\n" +
                "애플페이 걍 카드 들고 다니세요 \n" +
                "\n" +
                "핸드폰 케이스에 카드 붙이고 쓰세요 \n" +
                "\n" +
                "그럼 애플페이 완성");
        work.setUser(user);
        work.setPostCid(11L);
        work.setCreatedAt(LocalDateTime.now());
        work.setUpdatedAt(LocalDateTime.now());

        workspaceRepository.save(work);

    }

    @Test
    void addProjectPost() {

        // postiCid = 11  ,  workspaceCid = 1

        UserEntity user = userRepository.findByUserId("devel1").get();


        ProjectEntity pject = new ProjectEntity();

        pject.setTitle("애플페이 완료했습니다");
        pject.setBody("핸드폰 뒤에 카드 붙이는게 꿀이져?\n" +
                "\n" +
                "아주 좋습니다  \n" +
                "\n" +
                "따로 핸드폰 설정 할 필요도 없어요 \n" +
                "\n" +
                "어플 깔 필요도 없어요 ");
        pject.setUser(user);
        pject.setPostCid(11L);
        pject.setWorkspaceCid(1L);
        pject.setCreatedAt(LocalDateTime.now());
        pject.setUpdatedAt(LocalDateTime.now());

        projectRepository.save(pject);

    }











}


