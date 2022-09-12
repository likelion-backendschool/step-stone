package com.likelion.stepstone.mypage;


import com.likelion.stepstone.authentication.PrincipalDetails;
import com.likelion.stepstone.post.PostService;
import com.likelion.stepstone.post.model.PostEntity;
import com.likelion.stepstone.post.model.PostVo;
import com.likelion.stepstone.user.UserService;
import com.likelion.stepstone.user.model.UserDto;
import com.likelion.stepstone.user.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;


@Controller
public class MypageController {

    private final UserService userService;
    private final PostService postService;

    public MypageController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping("/mypage")
    // Todo : 게시글 띄우기 부분 구현 (해당 user가 작성한 게시글 보여주기 / Role에 따라 다름)
    public String showMypage(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model, @RequestParam(defaultValue = "0") int page) {

        // 로그인을 하지 않았으면 마이페이지 접속시 로그인 띄우기
        if (principalDetails == null) {
            return "redirect:/login";
        }

        UserEntity userEntity = principalDetails.getUser();

        String userName = userEntity.getName();
        String userRole = userEntity.getRole();
        // Role이 사용자 -> post 디비에서 usercid로 가져오기
        // Role이 개발자 -> project 디비에서 usercid로 가져오기
        if (userRole.equals("ROLE_USER")) {
            Page<PostEntity> paging = postService.getPostList(page);
            model.addAttribute("paging", paging);
        }
        /*
        @GetMapping("/list")
        public String list(Model model, @RequestParam(defaultValue = "0") int page) {
            Page<PostEntity> paging = postService.getPostList(page);
            model.addAttribute("paging", paging);
            return "post/list";
        }
         */

        model.addAttribute("userName", userName);

        return "mypage/mypage";
    }

    @GetMapping("/mypage/update")
    public String updatePasswordFrom(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {

        // 로그인을 하지 않았으면 마이페이지 접속시 로그인 띄우기
        if (principalDetails == null) {
            return "redirect:/login";
        }

        UserEntity userEntity = principalDetails.getUser();

        model.addAttribute("user", userEntity);
        model.addAttribute(new MypageForm());

        return "mypage/changePasswordForm";
    }

    @PostMapping("/mypage/update")
    public String updatePassword(@AuthenticationPrincipal PrincipalDetails principalDetails, @Valid MypageForm mypageForm, BindingResult bindingResult, Model model) {

        // 로그인을 하지 않았으면 접속시 로그인 띄우기
        if (principalDetails == null) {
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()) {
            return "mypage/changePasswordForm";
        }

        UserEntity userEntity = principalDetails.getUser();

        this.userService.updateUserPassword(UserDto.toDto(userEntity), mypageForm.getNewPasswordConfirm());
        model.addAttribute("message", "비밀번호를 변경했습니다.");
        model.addAttribute("searchUrl", "/mypage");

//        return "redirect:/mypage/update";
        return "user/message";
    }

}
