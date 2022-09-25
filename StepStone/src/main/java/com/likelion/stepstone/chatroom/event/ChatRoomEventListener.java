package com.likelion.stepstone.chatroom.event;

import com.likelion.stepstone.chat.ChatRepository;
import com.likelion.stepstone.chatroom.ChatRoomRepository;
import com.likelion.stepstone.chatroom.exception.DataNotFoundException;
import com.likelion.stepstone.chatroom.model.ChatRoomEntity;
import com.likelion.stepstone.notification.NotificationRepository;
import com.likelion.stepstone.notification.model.NotificationDto;
import com.likelion.stepstone.notification.model.NotificationEntity;
import com.likelion.stepstone.notification.model.NotificationType;
import com.likelion.stepstone.user.UserRepository;
import com.likelion.stepstone.user.model.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;

@Slf4j
@Async
@Transactional
@Component
@RequiredArgsConstructor
public class ChatRoomEventListener {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @EventListener // @EventListener 애너테이션을 이용해 이벤트 리스너를 명시합니다.
    public void handleChatRoomCreatedEvent(ChatRoomCreatedEvent chatRoomCreatedEvent){ // EventPublisher를 통해 이벤트가 발생될 때 전달한 파라미터가 StudyCreatedEvent일 때 해당 메서드가 호출됩니다.
        ChatRoomEntity chatRoomEntity = chatRoomCreatedEvent.getChatRoomEntity();
        log.info(chatRoomEntity.getRoomName() + " is created");

        NotificationEntity notificationEntity = createCreateNotification(chatRoomCreatedEvent.getChatRoomEntity(), chatRoomCreatedEvent.getUserEntity());
        // TODO DB에 Notification 정보 저장

        notificationRepository.save(notificationEntity);
    }

    @EventListener
    public void handleChatRoomInviteEvent(ChatRoomInviteEvent chatRoomInviteEvent){
        ChatRoomEntity chatRoomEntity = chatRoomInviteEvent.getChatRoomEntity();
        UserEntity userEntity = chatRoomInviteEvent.getUserEntity();

        log.info(userEntity.getName() + "is invited to" + chatRoomEntity.getRoomName());

        NotificationEntity notificationEntity = createInviteNotification(chatRoomEntity, userEntity);
        notificationRepository.save(notificationEntity);

    }

    @EventListener
    public void handleInquireEvent(ChatRoomInquireEvent chatRoomInquireEvent){
        UserEntity user = chatRoomInquireEvent.getUser();
        UserEntity developer = chatRoomInquireEvent.getDeveloper();
        ChatRoomEntity chatRoomEntity = chatRoomInquireEvent.getChatRoomEntity();

        NotificationEntity notificationUserEntity = createInquireUserNotification(developer, user, chatRoomEntity);
        NotificationEntity notificationDeveloperEntity = createInquireDeveloperNotification(developer, user, chatRoomEntity);
        notificationRepository.save(notificationUserEntity);
        notificationRepository.save(notificationDeveloperEntity);
    }


    private NotificationEntity createCreateNotification(ChatRoomEntity chatRoomEntity, UserEntity userEntity){
        NotificationEntity notificationEntity = NotificationEntity.builder()
                .title("채팅방 생성")
                .message(chatRoomEntity.getRoomName() + " 채팅방이 생성되었습니다.")
                .checked(false)
                .notificationType(NotificationType.CHAT_ROOM_CREATED)
                .userEntity(userEntity)
                .chatRoomEntity(chatRoomEntity)
                .build();


        return notificationEntity;
    }

    private NotificationEntity createInviteNotification(ChatRoomEntity chatRoomEntity, UserEntity userEntity){
        NotificationEntity notificationEntity = NotificationEntity.builder()
                .title("채팅방 초대")
                .message(chatRoomEntity.getRoomName() + " 채팅방으로 초대되었습니다. 채팅방에 참가하시려면 확인 버튼을 눌러주세요.")
                .checked(false)
                .notificationType(NotificationType. CHAT_ROOM_INVITE_REQUEST)
                .userEntity(userEntity)
                .chatRoomEntity(chatRoomEntity)
                .build();


        return notificationEntity;
    }

    private NotificationEntity createInquireUserNotification(UserEntity developer, UserEntity user, ChatRoomEntity chatRoomEntity){
        NotificationEntity notificationEntity = NotificationEntity.builder()
                .title("문의 요청")
                .message(developer.getName() + " 으로 부터 " +user.getName() +"님이 작성하신 게시글에 대한 문의가 요청됐습니다. 대화를 나누시려면 확인 버튼을 눌러주세요. 채팅방이 생성됩니다.")
                .checked(false)
                .notificationType(NotificationType.INQUIRE_REQUEST)
                .userEntity(user)
                .chatRoomEntity(chatRoomEntity)
                .build();


        return notificationEntity;
    }

    private NotificationEntity createInquireDeveloperNotification(UserEntity developer, UserEntity user, ChatRoomEntity chatRoomEntity){
        NotificationEntity notificationEntity = NotificationEntity.builder()
                .title("문의 요청 전송")
                .message(developer.getName() + "님이 요청하신 문의가 " +user.getName() +"님에게 요청되었습니다. "  +user.getName() + "님이 확인하시면 자동으로 채팅방이 생성됩니다.")
                .checked(false)
                .notificationType(NotificationType.INQUIRE_SEND)
                .userEntity(developer)
                .chatRoomEntity(chatRoomEntity)
                .build();


        return notificationEntity;
    }


}
