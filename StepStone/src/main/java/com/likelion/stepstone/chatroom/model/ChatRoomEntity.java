package com.likelion.stepstone.chatroom.model;


import com.likelion.stepstone.user.model.UserEntity;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "chat_rooms")
public class ChatRoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="chat_room_cid")
    Long chatRoomCid;

    @Setter
    @Column(name = "chat_room_id")
//    @Type(type = "uuid-char")
    String chatRoomId;

    @Setter
    @Column(name = "post_cid")
    Long postCid;

    @Setter
    @Column(name = "room_name")
    String roomName;

    @Setter
    @Column(name = "user_count")
    Integer userCount;

    @Setter
    @OneToMany
    @JoinTable(
            name = "chat_rooms_users",    // 연결테이블 이름
            joinColumns = @JoinColumn(name = "chat_room_cid"),// 유저와 매핑할 조인 컬럼 정보를 지정
            inverseJoinColumns = @JoinColumn(name = "user_cid")// 채팅방과 매핑할 조인 컬럼 정보를 지정
    )
    Set<UserEntity> users;

    @Setter
    @Column(name = "created_at")
    @CreatedDate
    LocalDateTime createdAt;

    @Setter
    @Column(name = "updated_at")
    @LastModifiedDate
    LocalDateTime updatedAt;

    public static ChatRoomEntity toEntity(ChatRoomDto chatRoomDto){
        ChatRoomEntity entity = ChatRoomEntity.builder()
                .chatRoomId(chatRoomDto.getChatRoomId())
                .postCid(chatRoomDto.getPostCid())
                .roomName(chatRoomDto.getRoomName())
                .userCount(chatRoomDto.getUserCount())
                .createdAt(chatRoomDto.getCreatedAt())
                .updatedAt(chatRoomDto.getUpdatedAt())
                .build();

        return entity;
    }
}
