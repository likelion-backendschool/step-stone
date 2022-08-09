package com.likelion.stepstone.config;

import com.likelion.stepstone.post.PostRepository;
import com.likelion.stepstone.post.PostService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    private final PostRepository postRepository;

    public SpringConfig(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Bean
    public PostService postService(){
        return new PostService(postRepository);
    }

}
