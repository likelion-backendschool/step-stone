package com.likelion.stepstone.post;

import com.likelion.stepstone.post.model.PostDto;
import com.likelion.stepstone.post.model.PostEntity;
import com.likelion.stepstone.post.model.PostVo;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RequestMapping("/post")
@Controller
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    @GetMapping("/form")
    public String createForm() {
        return "/post/form";
    }

    @GetMapping("/detail/{postCid}")
    public String detail(Model model,@PathVariable Long postCid) {

        model.addAttribute("post",postService.getPost(postCid));
        
        return "post/detail";
    }

    @PostMapping("/create")
    public String create(PostDto postDto) {


        postService.create(postDto);

        return "redirect:/post/read/1";
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


//    @GetMapping("/list")
//    public String boardList(Model model) {
//
//        model.addAttribute("list", PostService.postList());
//        return "boardList";
//    }

        @GetMapping("/read/{pageNo}")
        public String findPaginated ( @PathVariable(value = "pageNo") int pageNo, Model model){

            Page<PostDto> page = postService.findPaginated(1, 3, "likes", "desc");
            List<PostDto> likePosts = page.getContent();

            model.addAttribute("likePosts", likePosts);

            Page<PostDto> currPage = postService.findPaginated(pageNo, 10, "createdAt", "desc");
            List<PostDto> listPosts = currPage.getContent();

            model.addAttribute("listPosts", listPosts);
            model.addAttribute("currentPage", pageNo);
            model.addAttribute("totalPages", currPage.getTotalPages());
            model.addAttribute("totalItems", currPage.getTotalElements());

            return "post/list";
        }
    }




