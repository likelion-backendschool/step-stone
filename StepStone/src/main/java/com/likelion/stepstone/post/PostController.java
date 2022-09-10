package com.likelion.stepstone.post;

import com.likelion.stepstone.like.LikeService;
import com.likelion.stepstone.like.model.LikeEntity;
import com.likelion.stepstone.post.model.PostDto;
import com.likelion.stepstone.post.model.PostEntity;
import com.likelion.stepstone.user.UserService;
import com.likelion.stepstone.user.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;


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

        UserEntity siteUser = userService.getUser(principal.getName());

        PostDto postDto = PostDto.builder()
                .title(postForm.getTitle())
                .body(postForm.getBody())
                .userCid(siteUser.getUserCid())
                .build();

        postService.create(postDto);

        return "redirect:/post/list";
    }

    @GetMapping("/list")
    public String list(Model model, @RequestParam(defaultValue = "0") int page
    ) {
//        // 하트 버튼 골라서 나오기, detail.html에 넘겨줄 객체 생성.
//        Long userCid = 1L;
//        LikeEntity likeEntity = likeService.getLikeEntity(userCid);
//        if(likeEntity != null){
//            model.addAttribute("likeEntity",likeEntity);
//        }else{
//            model.addAttribute("likeEntity",likeEntity);
//        }


        Page<PostEntity> paging = postService.getList(page);
        model.addAttribute("paging", paging);
        return "post/list";
    }

    @GetMapping("/modify/{postCid}")
    public String postModifyGet(@PathVariable long postCid , PostForm postForm) {
        // @Valid PostForm postForm
        PostEntity postEntity = postService.getPostEntity(postCid);

        postForm.setTitle(postEntity.getTitle());
        postForm.setBody(postEntity.getBody());
        return "post/form";
    }
    @PostMapping("/modify/{postCid}")
    public String postModifyPost(@PathVariable long postCid , PostForm postForm) {

        PostEntity postEntity = postService.getPostEntity(postCid);
        postService.modify(postEntity, postForm.getTitle(), postForm.getBody());

        return "redirect:/post/detail/{postCid}";
    }

    @GetMapping("/detail/{postCid}")
    public String detail(Model model, @PathVariable  long postCid) {
        // 하트 버튼 골라서 나오기, detail.html에 넘겨줄 객체 생성.
        Long userCid = 1L;
        String exist = "notnull";
        String notexist = "null";
        LikeEntity likeEntity = likeService.getLikeEntity(postCid, userCid);
       if(likeEntity != null){
           model.addAttribute("likeEntity",exist);
       }else{
           model.addAttribute("likeEntity",notexist);
       }

        PostEntity postEntity = postService.getPostEntity(postCid);
        model.addAttribute("postEntity", postEntity);
        return "post/detail";
    }

    @GetMapping("/delete/{postCid}")
    public String delete( @PathVariable long postCid) {
        PostEntity postEntity = postService.getPostEntity(postCid);

        postService.delete(postEntity);

        return "redirect:/post/list";
    }

}




