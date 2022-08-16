package com.likelion.stepstone.config;

import com.likelion.stepstone.like.LikeRepository;
import com.likelion.stepstone.like.LikeService;
import com.likelion.stepstone.post.PostRepository;
import com.likelion.stepstone.post.PostService;
import com.likelion.stepstone.user.UserRepository;
import com.likelion.stepstone.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SpringConfig {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    public SpringConfig(PostRepository postRepository, LikeRepository likeRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
    }

    @Bean
    public PostService postService(){
        return new PostService(postRepository);
    }

    @Bean
    public LikeService likeService(){
        return new LikeService(likeRepository,postRepository);
    }

    @Bean
    public UserService userService(){
        return new UserService(userRepository);
    }


}
