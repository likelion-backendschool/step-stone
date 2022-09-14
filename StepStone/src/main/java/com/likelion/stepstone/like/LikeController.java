package com.likelion.stepstone.like;

import com.likelion.stepstone.user.UserService;
import com.likelion.stepstone.user.model.UserDto;
import lombok.AllArgsConstructor;
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
    public String like(Model model,Principal principal, @PathVariable Long postCid) {
        if (principal == null) {
            model.addAttribute("msg","로그인 후 이용해주세요");
            return "like/login";
        }

        UserDto user = userService.getUser(principal.getName());
        likeService.idCheck2(postCid, user);
        return "redirect:/post/detail/{postCid}";
    }

    }
