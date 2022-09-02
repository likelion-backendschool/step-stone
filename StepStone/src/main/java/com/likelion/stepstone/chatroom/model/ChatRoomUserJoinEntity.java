package com.likelion.stepstone.chatroom.model;

import com.likelion.stepstone.user.model.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="chat_rooms_users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomUserJoinEntity implements Serializable
{

    @EmbeddedId
    private ChatRoomUserJoinId id;

    @ManyToOne
    @MapsId("chatRoomCid") //This is the name of attr in EmployerDeliveryAgentPK class
    @JoinColumn(name = "chat_room_cid")
    private ChatRoomEntity chatRoomEntity;

    @ManyToOne
    @MapsId("userCid")
    @JoinColumn(name = "user_cid")
    private UserEntity userEntity;


    @Column(name = "profile_image_url")
    private String profileImageUrl;
}
