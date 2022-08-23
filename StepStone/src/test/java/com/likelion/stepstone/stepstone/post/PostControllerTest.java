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

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class PostControllerTest {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;



//    @Test
//    void save(){
//        PostEntity p = new PostEntity();
//        p.setPostId(UUID.randomUUID());
//        p.setPostCid(1L);
//        p.setTitle("하이");
//        p.setBody("버거킹 시켜주세요");
//        p.setLikes(0);
//        p.setUserId(UUID.fromString("4dc520a2-ebab-4bed-b334-e31b03af8c7d"));
//        p.setCreatedAt(LocalDateTime.now());
//        p.setUpdatedAt(LocalDateTime.now());
//        postRepository.save(p);
//
//        assertEquals("하이", p.getTitle());
//
//    }
//
//    @Test
//    void createUser(){
//        UserEntity u = new UserEntity();
//                u.setUserId(UUID.randomUUID());
//                u.setName("편재원");
//                u.setPassword(UUID.randomUUID());
//                u.setRole("사장");
//                u.setCreatedAt(LocalDateTime.now());
//                u.setUpdatedAt(LocalDateTime.now());
//
//
//        userRepository.save(u);
//
//        assertEquals("편재원",u.getName());
//    }




}
