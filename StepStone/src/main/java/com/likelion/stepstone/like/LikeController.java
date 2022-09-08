package com.likelion.stepstone.like;

import com.likelion.stepstone.user.UserRepository;
import com.likelion.stepstone.user.model.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@AllArgsConstructor
public class LikeController {

    private LikeService likeService;
    private UserRepository userRepository;  // userId 받아오기 위한 임시 사용

    @RequestMapping("/post/{postCid}/likes")
    public String like(@PathVariable Long postCid) {

        //UserEntity user = userRepository.findByName("No3").get(); //로그인 정보로 UserId 받아와야 함.(임시)
       // Long userCid = user.getUserCid();
        Long userCid = 1L;
        likeService.idCheck2(postCid, userCid);
        return "redirect:/post/list?page=0";
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
