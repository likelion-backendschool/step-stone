package com.likelion.stepstone.chatroom.event;

import com.likelion.stepstone.chatroom.model.ChatRoomEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Async
@Transactional(readOnly = true)
@Component
public class ChatRoomEventListener {
    @EventListener // @EventListener 애너테이션을 이용해 이벤트 리스너를 명시합니다.
    public void handleChatRoomCreatedEvent(ChatRoomCreatedEvent chatRoomCreatedEvent){ // EventPublisher를 통해 이벤트가 발생될 때 전달한 파라미터가 StudyCreatedEvent일 때 해당 메서드가 호출됩니다.
        ChatRoomEntity chatRoomEntity = chatRoomCreatedEvent.getChatRoomEntity();
        log.info(chatRoomEntity.getRoomName() + " is created");

        // TODO 이메일 보내거나 DB에 Notification 정보 저장
    }
}
