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

            return "index";
        }

        // 게시글이 6개 ~ 8개 -> like 정렬 -> 최근 이슈 게시글에는 top 6까지 띄우기 (slide 1, 2 사용)
        if (postVoListSize >= 6) {
            List<PostVo> postVoList1 = new ArrayList<>(postVoList.subList(0, 3));
            List<PostVo> postVoList2 = new ArrayList<>(postVoList.subList(3, 6));

            model.addAttribute("postSize", postVoList.size());
            model.addAttribute("posts1", postVoList1);
            model.addAttribute("posts2", postVoList2);

            return "index";
        }

        // 게시글이 3개 ~ 5개 -> like 정렬 -> 최근 이슈 게시글에는 그 중 top 3만 띄우기 (slide 1만 사용)
        if (postVoListSize > 2 && postVoListSize < 6) {
            List<PostVo> postVoList1 = new ArrayList<>(postVoList.subList(0, 3));

            model.addAttribute("postSize", postVoList.size());
            model.addAttribute("posts1", postVoList1);

            return "index";
        }

        // 게시글이 1개 or 2개 -> like 정렬 -> 최근 이슈 게시글에 우선 모두 띄우기 (slide 1만 사용)
        if (postVoListSize > 0) {
            model.addAttribute("postSize", postVoList.size());
            model.addAttribute("posts1", postVoList);

            return "index";
        }

        // 게시글이 존재하지 않는 경우 (0개 이하)
        else {
            model.addAttribute("postSize", postVoList.size());
        }

        return "index";
    }

}
