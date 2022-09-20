package com.likelion.stepstone.post;

import com.likelion.stepstone.authentication.PrincipalDetails;
import com.likelion.stepstone.like.LikeService;
import com.likelion.stepstone.like.model.LikeDto;
import com.likelion.stepstone.like.model.LikeEntity;
import com.likelion.stepstone.post.model.PostDto;
import com.likelion.stepstone.post.model.PostEntity;
import com.likelion.stepstone.post.model.PostVo;
import com.likelion.stepstone.user.UserService;
import com.likelion.stepstone.user.model.UserDto;
import com.likelion.stepstone.user.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RequestMapping("/post")
@Controller
public class PostController {

    private final PostService postService;

    private final LikeService likeService;
    private final UserService userService;

    public PostController(PostService postService ,LikeService likeService,UserService userService ) {
        this.postService = postService;
        this.likeService = likeService;
        this.userService = userService;
    }

    @GetMapping("/create")
    public String createForm(PostForm postForm) {
        return "/post/form";
    }

    @PostMapping("/create")
    public String create(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model, PostForm postForm) {

        //유효성 체크
        boolean hasError = false;

        if (postForm.getTitle() == null || postForm.getTitle().trim().length() == 0) {
            model.addAttribute("titleErrorMsg", "제목을 입력해주세요");
            hasError = true;
        }

        if (postForm.getBody() == null || postForm.getBody().trim().length() == 0) {
            model.addAttribute("bodyErrorMsg", "내용을 입력해주세요");
            hasError = true;
        }

        if (hasError) {
            model.addAttribute("postForm", postForm);
            return "post/form";
        }

        PostDto postDto = PostDto.builder()
                .title(postForm.getTitle())
                .body(postForm.getBody())
                .build();

        postService.create(postDto, principalDetails);

        return "redirect:/post/list";
    }

    @GetMapping("/list")
    public String list(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model, @RequestParam(defaultValue = "0") int page ) {


        Page<PostEntity> paging = postService.getList(page, principalDetails);
        model.addAttribute("paging", paging);


        // 최근 이슈 게시글 부분
        List<PostVo> postVoList = postService.getSortedPostList();
        int postVoListSize = postVoList.size();

        // 게시글이 9개 이상 -> like 정렬 -> 최근 이슈 게시글에는 top 9까지 띄우기 (slide 1, 2 사용)
        if (postVoListSize >= 9) {
            List<PostVo> postVoList1 = new ArrayList<>(postVoList.subList(0, 3));
            List<PostVo> postVoList2 = new ArrayList<>(postVoList.subList(3, 6));
            List<PostVo> postVoList3 = new ArrayList<>(postVoList.subList(6, 9));

            model.addAttribute("postSize", postVoList.size());
            model.addAttribute("posts1", postVoList1);
            model.addAttribute("posts2", postVoList2);
            model.addAttribute("posts3", postVoList3);

            return "post/list";
        }

        // 게시글이 6개 ~ 8개 -> like 정렬 -> 최근 이슈 게시글에는 top 6까지 띄우기 (slide 1, 2 사용)
        if (postVoListSize >= 6) {
            List<PostVo> postVoList1 = new ArrayList<>(postVoList.subList(0, 3));
            List<PostVo> postVoList2 = new ArrayList<>(postVoList.subList(3, 6));

            model.addAttribute("postSize", postVoList.size());
            model.addAttribute("posts1", postVoList1);
            model.addAttribute("posts2", postVoList2);

            return "post/list";
        }

        // 게시글이 3개 ~ 5개 -> like 정렬 -> 최근 이슈 게시글에는 그 중 top 3만 띄우기 (slide 1만 사용)
        if (postVoListSize > 2 && postVoListSize < 6) {
            List<PostVo> postVoList1 = new ArrayList<>(postVoList.subList(0, 3));

            model.addAttribute("postSize", postVoList.size());
            model.addAttribute("posts1", postVoList1);

            return "post/list";
        }

        // 게시글이 1개 or 2개 -> like 정렬 -> 최근 이슈 게시글에 우선 모두 띄우기 (slide 1만 사용)
        if (postVoListSize > 0) {
            model.addAttribute("postSize", postVoList.size());
            model.addAttribute("posts1", postVoList);
        }

        // 게시글이 존재하지 않는 경우 (0개 이하)
        else {
            model.addAttribute("postSize", postVoList.size());
        }

        return "post/list";
    }

    @PostAuthorize("hasRole('ROLE_ADMIN') or #postForm.userId == authentication.principal.username")
    @GetMapping("/modify/{postCid}")
    public String postModifyGet(@PathVariable long postCid, PostForm postForm) {
        // @Valid PostForm postForm
        PostDto postDto = postService.getPostDto(postCid);

        postForm.setTitle(postDto.getTitle());
        postForm.setBody(postDto.getBody());
        return "post/form";
    }



    @PreAuthorize("hasRole('ROLE_ADMIN') or #postForm.userId == authentication.principal.username")
    @PostMapping("/modify/{postCid}")
    public String postModifyPost(@PathVariable long postCid, PostForm postForm) {

        PostDto postDto = postService.getPostDto(postCid);
        postService.modify(postDto, postForm.getTitle(), postForm.getBody());

        return "redirect:/post/detail/{postCid}";
    }
    @GetMapping("/detail/{postCid}")
    public String detail(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model, @PathVariable long postCid) {

        String exist = "notnull";
        String notexist = "null";

        LikeDto likeDto = likeService.getLikeDto(postCid, principalDetails);

        if (likeDto != null) {
            model.addAttribute("likeEntity", exist);
        } else {
            model.addAttribute("likeEntity", notexist);
        }

        PostDto postDto = postService.getPostDto(postCid);
        model.addAttribute("postEntity", postDto);
        return "post/detail";
    }

    @GetMapping("/delete/{postCid}")
    public String delete(@PathVariable long postCid) {
        PostDto postDto = postService.getPostDto(postCid);
        postService.delete(postDto);

        return "redirect:/post/list";
    }

    @GetMapping("/postup/{postCid}")
    public String postUp(@PathVariable long postCid, Model model) {
        PostDto postDto = postService.getPostDto(postCid);

        LocalDateTime now = LocalDateTime.now();
        if (createDate(postDto)) {
            model.addAttribute("msg", "게시 글 작성 후 5일 이후부터 끌어올리기가 가능합니다.");
            model.addAttribute("postCid", postCid);
            return "post/alert";
        }

        postService.postUp(postDto);
        return "redirect:/post/list";

    }

    private boolean createDate(PostDto postDto) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime updatedAt = postDto.getUpdatedAt();

        Date toDay = java.sql.Timestamp.valueOf(now);
        Date createDay = java.sql.Timestamp.valueOf(updatedAt);

        long diffSec = (toDay.getTime() - createDay.getTime()) / 1000; //초 차이
        long diffDays = diffSec / (24 * 60 * 60);

        if (diffDays < 5) {
            return true;
        }
        return false;
    }

}