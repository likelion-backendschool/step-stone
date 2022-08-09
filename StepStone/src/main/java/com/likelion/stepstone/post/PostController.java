package com.likelion.stepstone.post;

import com.likelion.stepstone.post.model.PostDto;
import com.likelion.stepstone.post.model.PostVo;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/create")
    public String create(@RequestParam String title, @RequestParam String body, @RequestParam UUID userId, Model model){

        PostDto postDto = PostDto.builder()
                .title(title)
                .body(body)
                .userId(userId)
                .build();


        postService.create(postDto);

        model.addAttribute("title", postDto.getTitle());
        model.addAttribute("body", postDto.getBody());

        return "post/create";
    }

    @GetMapping("/read/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model) {

        Page<PostVo> page = postService.findPaginated(1, 3, "likes", "desc");
        List<PostVo> likePosts = page.getContent();

        model.addAttribute("likePosts", likePosts);

        Page<PostVo> currPage = postService.findPaginated(pageNo, 10, "createdAt", "desc");
        List<PostVo> listPosts = currPage.getContent();

        model.addAttribute("listPosts", listPosts);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", currPage.getTotalPages());
        model.addAttribute("totalItems", currPage.getTotalElements());
        return "index";
    }
}
