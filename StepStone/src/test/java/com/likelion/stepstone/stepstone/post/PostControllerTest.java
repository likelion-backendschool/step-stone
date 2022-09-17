package com.likelion.stepstone.stepstone.post;

import com.likelion.stepstone.post.PostRepository;
import com.likelion.stepstone.post.model.PostEntity;
import com.likelion.stepstone.user.UserRepository;
import com.likelion.stepstone.user.model.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDateTime;
import java.util.UUID;


@SpringBootTest
public class PostControllerTest {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;


    @Test
    void addTestPost() {
        UserEntity user = userRepository.findByUserId("test5")
                .orElse(null);

        for (Long i = 1L; i <= 10L; i++) {
            PostEntity p = new PostEntity();
            p.setPostId(UUID.randomUUID());
            p.setPostCid(i);
            p.setTitle("짧은 글");
            p.setBody( i +"번\n 저작자·발명가·과학기술자와 예술가의 권리는 법률로써 보호한다. 대법원장은 국회의 동의를 얻어 대통령이 임명한다. 모든 국민은 근로의 권리를 가진다. 국가는 사회적·경제적 방법으로 근로자의 고용의 증진과 적정임금의 보장에 노력하여야 하며, 법률이 정하는 바에 의하여 최저임금제를 시행하여야 한다.\n" +
                            "\n" +
                            "모든 국민은 법률이 정하는 바에 의하여 공무담임권을 가진다. 대법관의 임기는 6년으로 하며, 법률이 정하는 바에 의하여 연임할 수 있다. 대통령은 국회에 출석하여 발언하거나 서한으로 의견을 표시할 수 있다.");
            p.setLikes(0);
            p.setUser(user);
            p.setCreatedAt(LocalDateTime.now());
            p.setUpdatedAt(LocalDateTime.now());
            postRepository.save(p);

        }

    }
}


