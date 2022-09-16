package com.likelion.stepstone.chat;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ChatRoomOnlineFinder {

    /**
     * key : RoomId
     * value : users userId
     */
    @Getter
    @Setter
    Map<String, List<String>> onlineUsers = new HashMap<>();
}
