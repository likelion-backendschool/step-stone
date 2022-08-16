package com.likelion.stepstone.like;


import com.likelion.stepstone.post.PostRepository;
import com.likelion.stepstone.user.UserRepository;
import com.likelion.stepstone.user.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@ResponseBody
public class LikeController {
    @Autowired
    private  LikeService likeService;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/post/{postId}/likes")
    @ResponseBody
    public String like(@PathVariable UUID postId){
        //PostEntity post = postRepository.findByTitle("title");

        UserEntity user = userRepository.findByName("No3");  //로그인 정보로 UserId 받아와야 함.
        UUID userId = user.getUserId();

//        UUID postId = post.getPostId();
//        LikeDto likeDto = LikeDto.builder()
//                .postId(postId)
//                .userId(userId)
//                .build();
//        likeService.idCheck(userId,likeDto);

        likeService.idCheck2(postId,userId);
        return "좋아요 등록되었습니다.";
    }



}
