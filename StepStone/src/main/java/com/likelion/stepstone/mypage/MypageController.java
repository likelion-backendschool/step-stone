package com.likelion.stepstone.mypage;


import com.likelion.stepstone.authentication.PrincipalDetails;
import com.likelion.stepstone.user.model.UserEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.constraints.Null;


@Controller
public class MypageController {

    @GetMapping("/mypage")
    // Todo : 게시글 띄우기 부분 구현 (해당 user가 작성한 게시글 보여주기 / Role에 따라 다름)
    public String showMypage(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {

        // 로그인을 하지 않았으면 마이페이지 접속시 로그인 띄우기
        if (principalDetails == null) {
            return "redirect:/login";
        }

        UserEntity userEntity = principalDetails.getUser();

        String userName = userEntity.getName();

        model.addAttribute("userName", userName);

        return "/mypage/mypage";
    }

}
