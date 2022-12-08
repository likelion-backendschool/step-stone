package com.likelion.stepstone.notification.handler;

import com.likelion.stepstone.chat.model.ChatDto;
import com.likelion.stepstone.chat.model.ChatEntity;
import com.likelion.stepstone.notification.model.ChatNotificationDto;
import com.likelion.stepstone.user.model.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Service
@Slf4j
public class ChatNotificationHandler {

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public SseEmitter add(SseEmitter emitter) {
        emitter.onTimeout(() -> timeout(emitter));
        emitter.onCompletion(() -> complete(emitter));

        emitters.add(emitter);
        log.info("new emitter added: {}", emitter);
        log.info("New one added, total consumer: {}", emitters.size());

        return emitter;
    }

    private void complete(SseEmitter emitter) {
        log.info("onCompletion callback");
        emitters.remove(emitter);
    }

    private void timeout(SseEmitter emitter) {
        log.info("onTimeout callback");
        emitters.remove(emitter);
    }

//    @Scheduled(fixedDelay = 3000)
    public void send(String userId, Object data) {
        emitters.forEach(sseEmitter -> {
//            ExecutorService sseMvcExecutor = Executors.newSingleThreadExecutor();
//            sseMvcExecutor.execute(() -> {
                try {
//                    for (int i = 0; true; i++) {
                        sseEmitter.send(SseEmitter.event()
                                .name(userId)
                                .data(data));
//                        Thread.sleep(5000);
//                    }
                } catch (Exception e) {
                    sseEmitter.complete();
                }
            });
//        });
    }

}
