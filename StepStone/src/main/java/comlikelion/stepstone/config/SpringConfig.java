package comlikelion.stepstone.config;

import comlikelion.stepstone.post.PostRepository;
import comlikelion.stepstone.post.PostService;
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
