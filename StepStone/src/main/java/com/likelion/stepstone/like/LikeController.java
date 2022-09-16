package com.likelion.stepstone.like;

import com.likelion.stepstone.authentication.PrincipalDetails;
import com.likelion.stepstone.user.UserService;
import com.likelion.stepstone.user.model.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
@AllArgsConstructor
public class LikeController {

    private LikeService likeService;
    private UserService userService;

    @RequestMapping("/post/{postCid}/likes")
    public String like(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long postCid) {
        if (principalDetails == null) {
            model.addAttribute("msg","로그인 후 이용해주세요");
            return "like/login";
        }

        likeService.idCheck2(postCid, principalDetails);
        return "redirect:/post/detail/{postCid}";
    }

    }
