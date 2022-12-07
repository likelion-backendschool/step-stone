package com.likelion.stepstone.chat.redis;

public class RedisKeyGenerator {
    private static final String CHAT_ROOM_KEY = "chat_room";

    public static String generateChatRoomKey(Long chatRoomCid){
        return CHAT_ROOM_KEY + ":" + chatRoomCid;
    }
}
