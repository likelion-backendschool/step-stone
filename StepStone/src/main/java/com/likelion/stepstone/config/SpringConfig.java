package com.likelion.stepstone.config;

import com.likelion.stepstone.chat.ChatRepository;
import com.likelion.stepstone.chat.ChatService;
import com.likelion.stepstone.chatroom.ChatRoomRepository;
import com.likelion.stepstone.chatroom.ChatRoomService;
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
    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;

    public SpringConfig(PostRepository postRepository, LikeRepository likeRepository, UserRepository userRepository, ChatRepository chatRepository, ChatRoomRepository chatRoomRepository) {
        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
        this.chatRoomRepository = chatRoomRepository;
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
    public ChatService chatService(){
        return new ChatService(chatRepository);
    }

    @Bean
    public ChatRoomService chatRoomService(){
        return new ChatRoomService(chatRoomRepository);
    }

}
