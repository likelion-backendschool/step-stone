package com.likelion.stepstone.chatroom.event;

import com.likelion.stepstone.chat.ChatRepository;
import com.likelion.stepstone.chatroom.ChatRoomRepository;
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
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final TemplateEngine templateEngine;

    @EventListener // @EventListener 애너테이션을 이용해 이벤트 리스너를 명시합니다.
    public void handleChatRoomCreatedEvent(ChatRoomCreatedEvent chatRoomCreatedEvent){ // EventPublisher를 통해 이벤트가 발생될 때 전달한 파라미터가 StudyCreatedEvent일 때 해당 메서드가 호출됩니다.
        ChatRoomEntity chatRoomEntity = chatRoomCreatedEvent.getChatRoomEntity();
        log.info(chatRoomEntity.getRoomName() + " is created");

        NotificationDto notificationDto = createNotification(chatRoomCreatedEvent.getChatRoomEntity(), chatRoomCreatedEvent.getUserEntity());
        // TODO DB에 Notification 정보 저장

        saveNotification(notificationDto);
    }

    private NotificationDto createNotification(ChatRoomEntity chatRoomEntity, UserEntity userEntity){
        NotificationDto dto = NotificationDto.builder()
                .title("채팅방 생성")
                .message(chatRoomEntity.getRoomName() + " 채팅방이 생성되었습니다.")
                .checked(false)
                .notificationType(NotificationType.CHAT_ROOM_CREATED)
                .userEntity(userEntity)
                .build();


        return dto;
    }


    private void saveNotification(NotificationDto dto){
        NotificationEntity notificationEntity = NotificationEntity.toEntity(dto);

        notificationRepository.save(notificationEntity);
    }
}
