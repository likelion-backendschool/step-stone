package com.likelion.stepstone.mypage;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class MypageController {

    @GetMapping("/mypage")
    // Todo : 로그인 정보에 따라 보여줘야하는 부분 추가 구현 필요
    public String showMypage() {

        return "/mypage/mypage";
    }

}
