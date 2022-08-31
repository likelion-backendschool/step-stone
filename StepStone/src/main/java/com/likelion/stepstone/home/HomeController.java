package com.likelion.stepstone.home;

import com.likelion.stepstone.post.PostService;
import com.likelion.stepstone.post.model.PostDto;
import com.likelion.stepstone.post.model.PostEntity;
import com.likelion.stepstone.post.model.PostVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final PostService postService;

    public HomeController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public String issuedPost(Model model) {
        List<PostVo> postVoList1 = postService.getPostList1();
        List<PostVo> postVoList2 = postService.getPostList2();
        List<PostVo> postVoList3 = postService.getPostList3();

        model.addAttribute("posts1", postVoList1);
        model.addAttribute("posts2", postVoList2);
        model.addAttribute("posts3", postVoList3);

        return "index";
    }

}
