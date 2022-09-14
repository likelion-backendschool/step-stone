package com.likelion.stepstone.like;

import com.likelion.stepstone.user.UserService;
import com.likelion.stepstone.user.model.UserDto;
import com.likelion.stepstone.user.model.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.security.Principal;


@Controller
@AllArgsConstructor
public class LikeController {

    private LikeService likeService;
    private UserService userService;

    @RequestMapping("/post/{postCid}/likes")
    public String like(Principal principal,@PathVariable Long postCid) {
    if(principal==null){
//        HttpServletResponse response = servletContainer.getResponse();
//        alertAndClose(response,"로그인 후 이용해주세요");

        return "redirect:/post/detail/{postCid}";
    }

//        UserEntity user = userService.getUser(principal.getName());
        UserDto user = userService.getUser(principal.getName());

        likeService.idCheck2(postCid, user);
        return "redirect:/post/detail/{postCid}";
    }

//    public static void alertAndClose(HttpServletResponse response, String msg) {
//        try {
//            response.setContentType("text/html; charset=utf-8");
//            PrintWriter w = response.getWriter();
//            w.write("<script>alert('"+msg+"');window.close();</script>");
//            w.flush();
//            w.close();
//        } catch(Exception e) {
//            e.printStackTrace();
//        }







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
