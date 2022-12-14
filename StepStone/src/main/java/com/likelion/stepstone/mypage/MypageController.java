package com.likelion.stepstone.mypage;


import com.likelion.stepstone.authentication.PrincipalDetails;
import com.likelion.stepstone.like.LikeService;
import com.likelion.stepstone.like.model.LikeEntity;
import com.likelion.stepstone.post.PostService;
import com.likelion.stepstone.post.model.PostDto;
import com.likelion.stepstone.post.model.PostEntity;
import com.likelion.stepstone.post.model.PostVo;
import com.likelion.stepstone.projects.ProjectService;
import com.likelion.stepstone.projects.model.ProjectEntity;
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
    private final ProjectService projectService;

    public MypageController(UserService userService, PostService postService, LikeService likeService, WorkSpaceService workSpaceService, ProjectService projectService) {
        this.userService = userService;
        this.postService = postService;
        this.likeService = likeService;
        this.workSpaceService = workSpaceService;
        this.projectService = projectService;
    }

    @GetMapping("/mypage")
    public String showMypage(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model, @RequestParam(defaultValue = "0") int page) {

        // ???????????? ?????? ???????????? ??????????????? ????????? ????????? ?????????
        if (principalDetails == null) {
            return "redirect:/login";
        }

        UserEntity userEntity = principalDetails.getUser();

        /*
        1) ????????? ??????, My ????????? ??????
         */
        String userName = userEntity.getName();
        String userRole = userEntity.getRole();
        Long userCid = userEntity.getUserCid();
        boolean loginBefore = userEntity.isLoginBefore();

        if (userRole.equals("ROLE_DEVELOPER")) {    // ?????????
            Page<WorkSpaceEntity> paging = workSpaceService.getMyWorkPostList(page, userCid);
            int pageSize = paging.getSize();
            model.addAttribute("paging", paging);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("userRole", "?????????");

            /*
            ???????????? ????????? ???, ????????? ???????????? ??? ????????? ?????????????????? ?????????
             */
            List<ProjectEntity> projectEntities = projectService.getProjectPosts(userCid); // 2. ????????? ?????? ??????????????? ???????????? ????????????
            int projectSize = projectEntities.size();
            model.addAttribute("myProject", projectEntities);
            model.addAttribute("myProjectSize", projectSize);
        }

        if (userRole.equals("ROLE_USER")) {     // ?????? ??????
            Page<PostEntity> paging = postService.getMyPostList(page, userCid);
            int pageSize = paging.getContent().size();
            model.addAttribute("paging", paging);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("userRole", "????????????");
        }

        model.addAttribute("userName", userName);
        model.addAttribute("oauthlogin", loginBefore);

        /*
        2) ????????? ?????? ?????????
         */
        UserDto userDto = UserDto.toDto(userEntity);
        List<LikeEntity> likeEntities = likeService.getLikeEntity(userDto);     // ?????? user??? like ?????? ????????? ??????
        List<PostEntity> likePostEntities = likeService.getLikedPost(likeEntities);     // like ?????? post??? ??????

        int likePostSize = likePostEntities.size();

        model.addAttribute("likedPost", likePostEntities);
        model.addAttribute("likedPostSize", likePostSize);

        return "mypage/mypage";
    }

    @GetMapping("/mypage/update")
    public String updatePasswordFrom(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {

        // ???????????? ?????? ???????????? ??????????????? ????????? ????????? ?????????
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

        // ???????????? ?????? ???????????? ????????? ????????? ?????????
        if (principalDetails == null) {
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()) {
            return "mypage/changePasswordForm";
        }

        UserEntity userEntity = principalDetails.getUser();

        this.userService.updateUserPassword(UserDto.toDto(userEntity), mypageForm.getNewPasswordConfirm());
        model.addAttribute("message", "??????????????? ??????????????????.");
        model.addAttribute("searchUrl", "/mypage");

//        return "redirect:/mypage/update";
        return "user/message";
    }

}
