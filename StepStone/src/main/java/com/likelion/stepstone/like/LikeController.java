package com.likelion.stepstone.like;

import com.likelion.stepstone.user.UserRepository;
import com.likelion.stepstone.user.model.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;


@Controller
@AllArgsConstructor
public class LikeController {

    private LikeService likeService;
    private UserRepository userRepository;  // userId 받아오기 위한 임시 사용


    @GetMapping("/post/{postId}/likes")
    @ResponseBody
    public UUID like(@PathVariable UUID postId) {

        UserEntity user = userRepository.findByName("No3").get(); //로그인 정보로 UserId 받아와야 함.(임시)
         UUID userId = user.getUserId();
        //UUID userId = b64aa550-dff3-4234-aed6-f456426fdc15;
        likeService.idCheck2(postId, userId);
        return userId;

    }

}
