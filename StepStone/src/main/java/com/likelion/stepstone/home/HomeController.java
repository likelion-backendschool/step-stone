package com.likelion.stepstone.home;

import com.likelion.stepstone.post.PostService;
import com.likelion.stepstone.post.model.PostDto;
import com.likelion.stepstone.post.model.PostEntity;
import com.likelion.stepstone.post.model.PostVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
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
        List<PostVo> postVoList = postService.getSortedPostList();

        List<PostVo> postVoList1 = new ArrayList<>(postVoList.subList(0, 3));
        List<PostVo> postVoList2 = new ArrayList<>(postVoList.subList(3, 6));
        List<PostVo> postVoList3 = new ArrayList<>(postVoList.subList(6, 9));

        model.addAttribute("posts1", postVoList1);
        model.addAttribute("posts2", postVoList2);
        model.addAttribute("posts3", postVoList3);

        return "index";
    }

}
