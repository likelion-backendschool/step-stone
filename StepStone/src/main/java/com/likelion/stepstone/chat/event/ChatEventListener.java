package com.likelion.stepstone.chat.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Async
@Transactional
@Component
@RequiredArgsConstructor
public class ChatEventListener {
//    private final ChatRoomRepository chatRoomRepository;
//    private final NotificationRepository notificationRepository;
//
//    public void handleChatSendEvent(ChatSendEvent chatSendEvent){ // EventPublisher를 통해 이벤트가 발생될 때 전달한 파라미터가 StudyCreatedEvent일 때 해당 메서드가 호출됩니다.
//        String chatRoomId = chatSendEvent.getChatRoomId();
//        String roomName = chatRoomRepository.findByChatRoomId(chatRoomId).orElseThrow(() -> new DataNotFoundException("room not found")).getRoomName();
//
//        log.info(roomName + ": new message arrived");
//
//        NotificationDto notificationDto = createNotification(roomName, chatSendEvent.getUserEntity());
//        // TODO DB에 Notification 정보 저장
//
//        saveNotification(notificationDto, chatSendEvent.getUserEntity(), cha);
//    }
//
//    private NotificationDto createNotification(String roomName, UserEntity userEntity){
//
//        NotificationDto dto = NotificationDto.builder()
//                .title("새로운 채팅")
//                .message(roomName + "채팅방에 새로운 채팅이 도착했습니다.")
//                .checked(false)
//                .notificationType(NotificationType.CHAT_SEND.toString())
//                .userCid(userEntity.getUserCid())
//                .build();
//
//
//        return dto;
//    }
//
//    private void saveNotification(NotificationDto dto, UserEntity userEntity, ChatRoomEntity chatRoomEntity){
//        NotificationEntity notificationEntity = NotificationEntity.toEntity(dto, userEntity, chatRoomEntity);
//
//        notificationRepository.save(notificationEntity);
//    }
}
