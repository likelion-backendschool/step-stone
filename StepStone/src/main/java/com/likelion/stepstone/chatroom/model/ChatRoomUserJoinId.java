package com.likelion.stepstone.chatroom.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomUserJoinId implements Serializable {
    @Column(name = "chat_room_cid")
    private Long chatRoomCid;

    @Column(name = "user_cid")
    private Long userCid;
}
