package com.likelion.stepstone.mypage;


import com.likelion.stepstone.authentication.PrincipalDetails;
import com.likelion.stepstone.like.LikeService;
import com.likelion.stepstone.like.model.LikeEntity;
import com.likelion.stepstone.post.PostService;
import com.likelion.stepstone.post.model.PostDto;
import com.likelion.stepstone.post.model.PostEntity;
import com.likelion.stepstone.post.model.PostVo;
import com.likelion.stepstone.user.UserService;
import com.likelion.stepstone.user.model.UserDto;
import com.likelion.stepstone.user.model.UserEntity;
import com.likelion.stepstone.workspaces.WorkSpaceService;
import com.likelion.stepstone.workspaces.model.WorkSpaceDto;
import com.likelion.stepstone.workspaces.model.WorkSpaceEntity;
import com.likelion.stepstone.workspaces.model.WorkSpaceVo;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class MypageController {

    private final UserService userService;
    private final PostService postService;
    private final LikeService likeService;
    private final WorkSpaceService workSpaceService;

    public MypageController(UserService userService, PostService postService, LikeService likeService, WorkSpaceService workSpaceService) {
        this.userService = userService;
        this.postService = postService;
        this.likeService = likeService;
        this.workSpaceService = workSpaceService;
    }

    @GetMapping("/mypage")
    public String showMypage(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model, @RequestParam(defaultValue = "0") int page) {

        // 로그인을 하지 않았으면 마이페이지 접속시 로그인 띄우기
        if (principalDetails == null) {
            return "redirect:/login";
        }

        UserEntity userEntity = principalDetails.getUser();

        // 사용자 정보, My 게시글 관리
        String userName = userEntity.getName();
        String userRole = userEntity.getRole();
        Long userCid = userEntity.getUserCid();
        boolean loginBefore = userEntity.isLoginBefore();

        if (userRole.equals("ROLE_DEVELOPER")) {    // 개발자
            Page<WorkSpaceEntity> paging = workSpaceService.getMyWorkPostList(page, userCid);
            model.addAttribute("paging", paging);
            model.addAttribute("userRole", "개발자");
        }

        if (userRole.equals("ROLE_USER")) {     // 일반 유저
            Page<PostEntity> paging = postService.getMyPostList(page, userCid);
            model.addAttribute("paging", paging);
            model.addAttribute("userRole", "일반유저");
        }

        model.addAttribute("userName", userName);
        model.addAttribute("oauthlogin", loginBefore);

        // 좋아요 누른 게시글
        UserDto userDto = UserDto.toDto(userEntity);
        List<LikeEntity> likeEntities = likeService.getLikeEntity(userDto);     // 해당 user의 like 관련 데이터 얻기
        List<PostEntity> likePostEntities = likeService.getLikedPost(likeEntities);     // like 누른 post들 찾기

        model.addAttribute("likedPost", likePostEntities);

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