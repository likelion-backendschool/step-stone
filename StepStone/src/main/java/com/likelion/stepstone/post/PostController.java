package com.likelion.stepstone.post;

import com.likelion.stepstone.like.LikeService;
import com.likelion.stepstone.like.model.LikeDto;
import com.likelion.stepstone.post.model.PostDto;
import com.likelion.stepstone.post.model.PostEntity;
import com.likelion.stepstone.post.model.PostVo;
import com.likelion.stepstone.user.UserService;
import com.likelion.stepstone.user.model.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.ArrayList;
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String createForm(PostForm postForm) {
        return "/post/form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")

    public String create(Principal principal,Model model, PostForm postForm) {

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

        UserDto user = userService.getUser(principal.getName());

        PostDto postDto = PostDto.builder()
                .title(postForm.getTitle())
                .body(postForm.getBody())
                .build();

        postService.create(postDto,user);

        return "redirect:/post/list";
    }

    @GetMapping("/list")
    public String list(Principal principal,Model model, @RequestParam(defaultValue = "0") int page ) {
        UserDto user;
        if(principal==null){  //로그인 안 했을 경우 게스트user로 사이트 이용
             user = userService.getUser("guest");
        } else{
             user = userService.getUser(principal.getName());
        }

        Page<PostEntity> paging = postService.getList(page,user);
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


            return "post/list";
    }

    @GetMapping("/modify/{postCid}")
    public String postModifyGet(@PathVariable long postCid , PostForm postForm) {
        // @Valid PostForm postForm
        PostDto postDto = postService.getPostDto(postCid);

        postForm.setTitle(postDto.getTitle());
        postForm.setBody(postDto.getBody());
        return "post/form";
    }
    @PostMapping("/modify/{postCid}")
    public String postModifyPost(@PathVariable long postCid , PostForm postForm) {

        PostDto postDto = postService.getPostDto(postCid);
        postService.modify(postDto, postForm.getTitle(), postForm.getBody());

        return "redirect:/post/detail/{postCid}";
    }

    @GetMapping("/detail/{postCid}")
    public String detail(Principal principal,Model model, @PathVariable  long postCid) {

        UserDto user;
        if(principal==null){
            user = userService.getUser("guest");
        } else{
            user = userService.getUser(principal.getName());  //현재 로그인 한 user
        }

        String exist = "notnull";
        String notexist = "null";

        LikeDto likeDto = likeService.getLikeDto(postCid, user);

        if(likeDto != null){
            model.addAttribute("likeEntity",exist);
        }else{
            model.addAttribute("likeEntity",notexist);
        }

        PostDto postDto = postService.getPostDto(postCid);
        model.addAttribute("postEntity", postDto);
        return "post/detail";
    }

    @GetMapping("/delete/{postCid}")
    public String delete( @PathVariable long postCid) {
        PostDto postDto = postService.getPostDto(postCid);

        postService.delete(postDto);

        return "redirect:/post/list";
    }
}
