package com.likelion.stepstone.stepstone.like;

import com.likelion.stepstone.like.LikeRepository;
import com.likelion.stepstone.like.LikeService;
import com.likelion.stepstone.like.model.LikeEntity;
import com.likelion.stepstone.post.PostRepository;
import com.likelion.stepstone.post.model.PostEntity;
import com.likelion.stepstone.user.UserRepository;
import com.likelion.stepstone.user.model.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class LikeControllerTest {

    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private LikeService likeService;

    @Test
    void contextLoads() {
    }

/*
    @Test
    public void addUserAndPost1(){
        UUID userId = UUID.randomUUID();
        UUID pw = UUID.randomUUID();

        UserEntity user1 = new UserEntity();

        user1.setName("No1");
        user1.setCreatedAt(LocalDateTime.now());
        user1.setPassword("pw");
        user1.setRole("user");
        user1.setUpdatedAt(LocalDateTime.now());
        user1.setUserId("userId");
        user1.setUserCid(1L);
        userRepository.save(user1);


        UUID postId = UUID.randomUUID();

        PostEntity post1 = new PostEntity();

        post1.setPostId(postId);
        post1.setCreatedAt(LocalDateTime.now());
        post1.setTitle("title1");
        post1.setBody("body1");
        post1.setLikes(0);
        post1.setUpdatedAt(LocalDateTime.now());
        // post1.setUserId(userId);
        post1.setPostCid(1L);
        postRepository.save(post1);

    }
    @Test
    public void addUserAndPost2() {
        UUID userId = UUID.randomUUID();
        UUID pw = UUID.randomUUID();

        UserEntity user2 = new UserEntity();

        user2.setName("No2");
        user2.setCreatedAt(LocalDateTime.now());
        user2.setPassword("pw");
        user2.setRole("user");
        user2.setUpdatedAt(LocalDateTime.now());
        user2.setUserId("userId2");
        user2.setUserCid(2L);
        userRepository.save(user2);


        UUID postId = UUID.randomUUID();

        PostEntity post2 = new PostEntity();

        post2.setPostId(postId);
        post2.setCreatedAt(LocalDateTime.now());
        post2.setTitle("title2");
        post2.setBody("body2");
        post2.setLikes(0);
        post2.setUpdatedAt(LocalDateTime.now());
       //  post2.setUserId(userId);
        post2.setPostCid(2L);
        postRepository.save(post2);

    }

    @Test
    public void addUserAndPost3() {
        UUID userId = UUID.randomUUID();
        UUID pw = UUID.randomUUID();

        UserEntity user3 = new UserEntity();

        user3.setName("No3");
        user3.setCreatedAt(LocalDateTime.now());
        user3.setPassword("pw");
        user3.setRole("user");
        user3.setUpdatedAt(LocalDateTime.now());
        user3.setUserId("userId3");
        user3.setUserCid(3L);
        userRepository.save(user3);


        UUID postId = UUID.randomUUID();

        PostEntity post3 = new PostEntity();

        post3.setPostId(postId);
        post3.setCreatedAt(LocalDateTime.now());
        post3.setTitle("title3");
        post3.setBody("body3");
        post3.setLikes(0);
        post3.setUpdatedAt(LocalDateTime.now());
        // post3.setUserId(userId);
        post3.setPostCid(3L);
        postRepository.save(post3);

    }
    @Test
    public void addUserAndPost4() {
        UUID userId = UUID.randomUUID();
        UUID pw = UUID.randomUUID();

        UserEntity user4 = new UserEntity();

        user4.setName("No4");
        user4.setCreatedAt(LocalDateTime.now());
        user4.setPassword("pw");
        user4.setRole("user");
        user4.setUpdatedAt(LocalDateTime.now());
        user4.setUserId("userId4");
        user4.setUserCid(4L);
        userRepository.save(user4);


        UUID postId = UUID.randomUUID();

        PostEntity post4 = new PostEntity();

        post4.setPostId(postId);
        post4.setCreatedAt(LocalDateTime.now());
        post4.setTitle("title4");
        post4.setBody("body4");
        post4.setUpdatedAt(LocalDateTime.now());
        // post4.setUserId(userId);
        post4.setPostCid(4L);
        postRepository.save(post4);

    }


    @Test
    void updateLikesCount(){
        //List<LikeEntity> likes = likeRepository.findByPostId(102898c2-ab59-4a80-97b3-31d3c73d2e83);
        List<LikeEntity> likes = likeRepository.findByPostCid(25L);
        PostEntity post = postRepository.findByPostCid(2L);
        int i = likes.size();
        post.setLikes(i);
        postRepository.save(post);

    }
     */

}



