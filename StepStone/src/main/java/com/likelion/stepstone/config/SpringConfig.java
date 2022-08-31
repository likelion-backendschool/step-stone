package com.likelion.stepstone.config;

import com.likelion.stepstone.like.LikeRepository;
import com.likelion.stepstone.like.LikeService;
import com.likelion.stepstone.post.PostRepository;
import com.likelion.stepstone.post.PostService;
import com.likelion.stepstone.projects.ProjectRepository;
import com.likelion.stepstone.projects.ProjectService;
import com.likelion.stepstone.user.UserRepository;
import com.likelion.stepstone.user.UserService;
import com.likelion.stepstone.workspaces.WorkSpaceRepository;
import com.likelion.stepstone.workspaces.WorkSpaceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SpringConfig {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    private final WorkSpaceRepository workSpaceRepository;

    private final ProjectRepository projectRepository;
    public SpringConfig(PostRepository postRepository, LikeRepository likeRepository, UserRepository userRepository, WorkSpaceRepository workSpaceRepository , ProjectRepository projectRepository) {
        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.workSpaceRepository = workSpaceRepository;
        this.projectRepository = projectRepository;
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

    @Bean
    public WorkSpaceService workSpaceService(){
        return new WorkSpaceService(workSpaceRepository);
    }

    @Bean
    public ProjectService projectService(){
        return new ProjectService(projectRepository);
    }
}
