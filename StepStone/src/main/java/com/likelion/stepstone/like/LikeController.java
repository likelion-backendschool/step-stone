package com.likelion.stepstone.like;

import com.likelion.stepstone.like.model.LikeDto;
import com.likelion.stepstone.like.model.LikeEntity;
import com.likelion.stepstone.post.model.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Controller
public class LikeController {
    @Autowired
    private  LikeService likeService;
    @PostMapping("/post/{postId}/likes")
    public void likes(@PathVariable UUID postId){
        UUID userId = UUID.randomUUID();

        LikeDto likeDto = LikeDto.builder()
                .postId(postId)
                .userId(userId)
                .build();

        likeService.likes(likeDto);
    }



}
