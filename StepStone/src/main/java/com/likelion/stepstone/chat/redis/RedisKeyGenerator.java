package com.likelion.stepstone.chat.redis;

import com.likelion.stepstone.config.CacheNames;

public class RedisKeyGenerator {
    private static final String CHAT_ROOM_KEY = "chat_room";
    private static final String CUT_IDX = "cut_idx";
    public static String generateChatRoomKey(Long chatRoomCid){
        return CHAT_ROOM_KEY + ":" + chatRoomCid;
    }

    public static String generateChatRoomKey(String chatRoomId){
        return CHAT_ROOM_KEY + ":" + chatRoomId;
    }
    public static String generateCutIdxKey(String chatRoomId){
        return CUT_IDX + ":" + chatRoomId;
    }
}
