package com.likelion.stepstone.chatroom.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatRoomEntity is a Querydsl query type for ChatRoomEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatRoomEntity extends EntityPathBase<ChatRoomEntity> {

    private static final long serialVersionUID = -1169726861L;

    public static final QChatRoomEntity chatRoomEntity = new QChatRoomEntity("chatRoomEntity");

    public final NumberPath<Long> chatRoomCid = createNumber("chatRoomCid", Long.class);

    public final StringPath chatRoomId = createString("chatRoomId");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final NumberPath<Long> postCid = createNumber("postCid", Long.class);

    public final StringPath roomName = createString("roomName");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> userCount = createNumber("userCount", Integer.class);

    public final SetPath<com.likelion.stepstone.user.model.UserEntity, com.likelion.stepstone.user.model.QUserEntity> users = this.<com.likelion.stepstone.user.model.UserEntity, com.likelion.stepstone.user.model.QUserEntity>createSet("users", com.likelion.stepstone.user.model.UserEntity.class, com.likelion.stepstone.user.model.QUserEntity.class, PathInits.DIRECT2);

    public QChatRoomEntity(String variable) {
        super(ChatRoomEntity.class, forVariable(variable));
    }

    public QChatRoomEntity(Path<? extends ChatRoomEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChatRoomEntity(PathMetadata metadata) {
        super(ChatRoomEntity.class, metadata);
    }

}

