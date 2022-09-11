package com.likelion.stepstone.like;

import com.likelion.stepstone.user.UserService;
import com.likelion.stepstone.user.model.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
@AllArgsConstructor
public class LikeController {

    private LikeService likeService;
    private UserService userService;

    @RequestMapping("/post/{postCid}/likes")
    public String like(Principal principal,@PathVariable Long postCid) {

        UserEntity user = userService.getUser(principal.getName());

        likeService.idCheck2(postCid, user);
        return "redirect:/post/detail/{postCid}";
    }

    // 좋아요 버튼 테스트
    @RequestMapping("/test3")
    public String likebutton3(){
        return "like/test3";
    }
    @RequestMapping("/test")
    public String likebutton1(){
        return "like/likebutton";
    }

}
