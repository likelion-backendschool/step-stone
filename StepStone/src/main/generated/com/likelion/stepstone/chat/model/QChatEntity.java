package com.likelion.stepstone.chat.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatEntity is a Querydsl query type for ChatEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatEntity extends EntityPathBase<ChatEntity> {

    private static final long serialVersionUID = -563584461L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChatEntity chatEntity = new QChatEntity("chatEntity");

    public final NumberPath<Long> chatCid = createNumber("chatCid", Long.class);

    public final StringPath chatId = createString("chatId");

    public final StringPath chatRoomId = createString("chatRoomId");

    public final StringPath createdAt = createString("createdAt");

    public final StringPath message = createString("message");

    public final com.likelion.stepstone.user.model.QUserEntity sender;

    public QChatEntity(String variable) {
        this(ChatEntity.class, forVariable(variable), INITS);
    }

    public QChatEntity(Path<? extends ChatEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChatEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChatEntity(PathMetadata metadata, PathInits inits) {
        this(ChatEntity.class, metadata, inits);
    }

    public QChatEntity(Class<? extends ChatEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.sender = inits.isInitialized("sender") ? new com.likelion.stepstone.user.model.QUserEntity(forProperty("sender")) : null;
    }

}

