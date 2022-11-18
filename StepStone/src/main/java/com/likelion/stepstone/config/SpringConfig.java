package com.likelion.stepstone.config;

import com.likelion.stepstone.chat.ChatRepository;
import com.likelion.stepstone.chat.ChatService;
import com.likelion.stepstone.chatroom.ChatRoomJoinRepository;
import com.likelion.stepstone.chatroom.ChatRoomRepository;
import com.likelion.stepstone.chatroom.ChatRoomService;
import com.likelion.stepstone.like.LikeRepository;
import com.likelion.stepstone.like.LikeService;
import com.likelion.stepstone.notification.repository.NotificationRepository;
import com.likelion.stepstone.post.PostRepository;
import com.likelion.stepstone.post.PostService;
import com.likelion.stepstone.projects.ProjectRepository;
import com.likelion.stepstone.projects.ProjectService;
import com.likelion.stepstone.user.UserRepository;
import com.likelion.stepstone.user.UserService;
import com.likelion.stepstone.workspaces.WorkSpaceRepository;
import com.likelion.stepstone.workspaces.WorkSpaceService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.messaging.simp.SimpMessageSendingOperations;


@Configuration
public class SpringConfig {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomJoinRepository chatRoomJoinRepository;
    private final SimpMessageSendingOperations messagingTemplate;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ApplicationEventPublisher eventPublisher;
    private final WorkSpaceRepository workSpaceRepository;
    private final ProjectRepository projectRepository;
    private final NotificationRepository notificationRepository;

    public SpringConfig(PostRepository postRepository,
                        LikeRepository likeRepository,
                        UserRepository userRepository,
                        ChatRepository chatRepository,
                        ChatRoomRepository chatRoomRepository,
                        ChatRoomJoinRepository chatRoomJoinRepository,
                        WorkSpaceRepository workSpaceRepository,
                        ProjectRepository projectRepository,
                        SimpMessageSendingOperations messagingTemplate,
                        BCryptPasswordEncoder bCryptPasswordEncoder, ApplicationEventPublisher eventPublisher, NotificationRepository notificationRepository) {

        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
        this.chatRoomRepository = chatRoomRepository;
        this.chatRoomJoinRepository = chatRoomJoinRepository;
        this.workSpaceRepository = workSpaceRepository;
        this.projectRepository = projectRepository;
        this.messagingTemplate = messagingTemplate;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.eventPublisher = eventPublisher;
        this.notificationRepository = notificationRepository;

    }

    @Bean
    public PostService postService() {
        return new PostService(postRepository , likeRepository);
    }

    @Bean
    public LikeService likeService() {
        return new LikeService(likeRepository, postRepository);
    }

    @Bean
    public UserService userService() {
        return new UserService(userRepository, bCryptPasswordEncoder);
    }

    @Bean
    public ChatService chatService() {
        return new ChatService(messagingTemplate, userRepository, chatRepository, chatRoomRepository, chatRoomJoinRepository, eventPublisher);
    }

    @Bean
    public ChatRoomService chatRoomService() {
        return new ChatRoomService(chatRoomRepository, userRepository, chatRoomJoinRepository, eventPublisher,notificationRepository);
    }
    @Bean
    public WorkSpaceService workSpaceService(){ return new WorkSpaceService(workSpaceRepository); }
    @Bean
    public ProjectService projectService(){ return new ProjectService(projectRepository); }
}
