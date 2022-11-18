package com.likelion.stepstone.notification.handler;

import com.likelion.stepstone.chat.model.ChatDto;
import com.likelion.stepstone.chat.model.ChatEntity;
import com.likelion.stepstone.notification.model.ChatNotificationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

@Service
public class ChatNotificationHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatNotificationHandler.class);

    private final List<Consumer<ChatNotificationDto>> listeners = new CopyOnWriteArrayList<>();

    public void subscribe(Consumer<ChatNotificationDto> listener){
        listeners.add(listener);
        LOGGER.info("New one added, total consumer: {}", listeners.size());
    }

    public void publish(ChatNotificationDto chatNotificationDto){
        LOGGER.info("Processing live chat : {}", chatNotificationDto);
        listeners.forEach(listener -> listener.accept(chatNotificationDto));
    }

}
