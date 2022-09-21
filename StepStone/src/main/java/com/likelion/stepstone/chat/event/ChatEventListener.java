package com.likelion.stepstone.chat.event;

import com.likelion.stepstone.chat.model.ChatDto;
import com.likelion.stepstone.chat.model.ChatEntity;
import com.likelion.stepstone.chatroom.ChatRoomRepository;
import com.likelion.stepstone.chatroom.event.ChatRoomCreatedEvent;
import com.likelion.stepstone.chatroom.exception.DataNotFoundException;
import com.likelion.stepstone.chatroom.model.ChatRoomDto;
import com.likelion.stepstone.chatroom.model.ChatRoomEntity;
import com.likelion.stepstone.notification.NotificationRepository;
import com.likelion.stepstone.notification.model.NotificationDto;
import com.likelion.stepstone.notification.model.NotificationEntity;
import com.likelion.stepstone.notification.model.NotificationType;
import com.likelion.stepstone.user.model.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Async
@Transactional
@Component
@RequiredArgsConstructor
public class ChatEventListener {
    private final ChatRoomRepository chatRoomRepository;
    private final NotificationRepository notificationRepository;

    public void handleChatSendEvent(ChatSendEvent chatSendEvent){ // EventPublisher를 통해 이벤트가 발생될 때 전달한 파라미터가 StudyCreatedEvent일 때 해당 메서드가 호출됩니다.
        String chatRoomId = chatSendEvent.getChatRoomId();
        String roomName = chatRoomRepository.findByChatRoomId(chatRoomId).orElseThrow(() -> new DataNotFoundException("room not found")).getRoomName();

        log.info(roomName + ": new message arrived");

        NotificationDto notificationDto = createNotification(roomName, chatSendEvent.getUserEntity());
        // TODO DB에 Notification 정보 저장

        saveNotification(notificationDto, chatSendEvent.getUserEntity());
    }

    private NotificationDto createNotification(String roomName, UserEntity userEntity){

        NotificationDto dto = NotificationDto.builder()
                .title("새로운 채팅")
                .message(roomName + "채팅방에 새로운 채팅이 도착했습니다.")
                .checked(false)
                .notificationType(NotificationType.CHAT_SEND.toString())
                .userCid(userEntity.getUserCid())
                .build();


        return dto;
    }

    private void saveNotification(NotificationDto dto, UserEntity userEntity){
        NotificationEntity notificationEntity = NotificationEntity.toEntity(dto, userEntity);

        notificationRepository.save(notificationEntity);
    }
}
