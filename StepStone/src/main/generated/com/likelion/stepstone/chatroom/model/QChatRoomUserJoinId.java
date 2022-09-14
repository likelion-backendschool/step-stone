package com.likelion.stepstone.chatroom.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QChatRoomUserJoinId is a Querydsl query type for ChatRoomUserJoinId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QChatRoomUserJoinId extends BeanPath<ChatRoomUserJoinId> {

    private static final long serialVersionUID = 1374129696L;

    public static final QChatRoomUserJoinId chatRoomUserJoinId = new QChatRoomUserJoinId("chatRoomUserJoinId");

    public final NumberPath<Long> chatRoomCid = createNumber("chatRoomCid", Long.class);

    public final NumberPath<Long> userCid = createNumber("userCid", Long.class);

    public QChatRoomUserJoinId(String variable) {
        super(ChatRoomUserJoinId.class, forVariable(variable));
    }

    public QChatRoomUserJoinId(Path<? extends ChatRoomUserJoinId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChatRoomUserJoinId(PathMetadata metadata) {
        super(ChatRoomUserJoinId.class, metadata);
    }

}

