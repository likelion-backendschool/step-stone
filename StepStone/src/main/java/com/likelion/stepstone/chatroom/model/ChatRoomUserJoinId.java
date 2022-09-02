package com.likelion.stepstone.chatroom.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ChatRoomUserJoinId implements Serializable {
    @Column(name = "chat_room_cid")
    private Long chatRoomCid;

    @Column(name = "user_cid")
    private Long userCid;
}
