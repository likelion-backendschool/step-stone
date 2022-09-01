package com.likelion.stepstone.post;

import com.likelion.stepstone.post.model.PostDto;
import com.likelion.stepstone.post.model.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RequestMapping("/post")
@Controller
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    @GetMapping("/create")
    public String createForm(PostForm postForm) {
        return "/post/form";
    }

    @PostMapping("/create")
    public String create(Model model, PostForm postForm) {

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


        postService.create(postDto);

        return "redirect:/post/list";
    }

    @GetMapping("/detail/{postCid}")
    public String detail(Model model,@PathVariable Long postCid) {

        model.addAttribute("post",postService.getPost(postCid));

        return "post/detail";
    }

    @GetMapping("/update")
    public String update(@RequestParam("postId") UUID postId, @RequestParam("title") String title, @RequestParam("body") String body, Model model) {
        PostDto postDto = PostDto.builder()
                .title(title)
                .body(body)
                .postId(postId)
                .build();
    
        postService.update(postDto);

        model.addAttribute("title", title);
        model.addAttribute("body", body);
        return "post/create";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long postCid, Model model) {
        PostDto postDto = PostDto.builder()
                .postCid(postCid)
                .build();

        postService.delete(postDto);
        model.addAttribute("postCid", postCid);
        return "post/delete";

    }
    @GetMapping("/list")
    public String list(Model model, @RequestParam(defaultValue = "1") int page) {
        Page<PostEntity> paging = postService.getPostList(page);
        model.addAttribute("paging", paging);
        return "post/list";
    }






//        @GetMapping("/list/{pageNo}")
//        public String findPaginated ( @PathVariable(value = "pageNo") int pageNo, Model model){
//
//            Page<PostDto> page = postService.findPaginated(1, 3, "likes", "desc");
//            List<PostDto> likePosts = page.getContent();
//
//            model.addAttribute("likePosts", likePosts);
//
//            Page<PostDto> currPage = postService.findPaginated(pageNo, 10, "createdAt", "desc");
//            List<PostDto> listPosts = currPage.getContent();
//
//            model.addAttribute("listPosts", listPosts);
//            model.addAttribute("currentPage", pageNo);
//            model.addAttribute("totalPages", currPage.getTotalPages());
//            model.addAttribute("totalItems", currPage.getTotalElements());
//
//            return "post/list";
//        }
    }




