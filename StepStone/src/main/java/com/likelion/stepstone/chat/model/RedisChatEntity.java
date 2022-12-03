package com.likelion.stepstone.chat.model;

import com.likelion.stepstone.chatroom.model.ChatRoomEntity;
import com.likelion.stepstone.user.model.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.*;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
@Getter
@Setter
@RedisHash("CHAT")
@Builder
public class RedisChatEntity implements Serializable {
    private static final long serialVersionUID = 6494678977089006629L;

    @Id
    String chatId;


    private @Indexed String chatRoomId;
    /**
     * ChatRoomEntity는 Builder가 아닌 setter로 설정해주어야 한다.
     */
//    @ManyToOne
//    @Indexed
//    ChatRoomEntity chatRoomEntity;

    String message;


    @ManyToOne
    @Indexed
    UserEntity sender;

//    @CreatedDate
    String createdAt;
}