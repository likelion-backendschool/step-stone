package com.likelion.stepstone.config;

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

    public SpringConfig(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Bean
    public PostService postService(){
        return new PostService(postRepository);
    }

    @Bean
    public UserService userService(){
        return new UserService(userRepository);
    }

}
