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
import org.springframework.messaging.simp.SimpMessageSendingOperations;


@Configuration
public class SpringConfig {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;


    private final SimpMessageSendingOperations messagingTemplate;
    public SpringConfig(PostRepository postRepository,
                        LikeRepository likeRepository,
                        UserRepository userRepository,
                        ChatRepository chatRepository,
                        ChatRoomRepository chatRoomRepository,
                        SimpMessageSendingOperations messagingTemplate)

            {
        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
        this.chatRoomRepository = chatRoomRepository;
        this.messagingTemplate = messagingTemplate;
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
        return new ChatService(messagingTemplate, userRepository, chatRepository);
    }

    @Bean
    public ChatRoomService chatRoomService(){
        return new ChatRoomService(chatRoomRepository, userRepository);
    }

}
