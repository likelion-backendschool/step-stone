package com.likelion.stepstone.chatroom.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatRoomUserJoinEntity is a Querydsl query type for ChatRoomUserJoinEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatRoomUserJoinEntity extends EntityPathBase<ChatRoomUserJoinEntity> {

    private static final long serialVersionUID = -752657816L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChatRoomUserJoinEntity chatRoomUserJoinEntity = new QChatRoomUserJoinEntity("chatRoomUserJoinEntity");

    public final QChatRoomEntity chatRoomEntity;

    public final QChatRoomUserJoinId id;

    public final StringPath profileImageUrl = createString("profileImageUrl");

    public final com.likelion.stepstone.user.model.QUserEntity userEntity;

    public QChatRoomUserJoinEntity(String variable) {
        this(ChatRoomUserJoinEntity.class, forVariable(variable), INITS);
    }

    public QChatRoomUserJoinEntity(Path<? extends ChatRoomUserJoinEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChatRoomUserJoinEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChatRoomUserJoinEntity(PathMetadata metadata, PathInits inits) {
        this(ChatRoomUserJoinEntity.class, metadata, inits);
    }

    public QChatRoomUserJoinEntity(Class<? extends ChatRoomUserJoinEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.chatRoomEntity = inits.isInitialized("chatRoomEntity") ? new QChatRoomEntity(forProperty("chatRoomEntity")) : null;
        this.id = inits.isInitialized("id") ? new QChatRoomUserJoinId(forProperty("id")) : null;
        this.userEntity = inits.isInitialized("userEntity") ? new com.likelion.stepstone.user.model.QUserEntity(forProperty("userEntity")) : null;
    }

}

