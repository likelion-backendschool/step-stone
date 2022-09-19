package com.likelion.stepstone.workspaces.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWorkSpaceEntity is a Querydsl query type for WorkSpaceEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWorkSpaceEntity extends EntityPathBase<WorkSpaceEntity> {

    private static final long serialVersionUID = -976899258L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWorkSpaceEntity workSpaceEntity = new QWorkSpaceEntity("workSpaceEntity");

    public final StringPath body = createString("body");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath title = createString("title");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final com.likelion.stepstone.user.model.QUserEntity user;

    public final NumberPath<Long> workspaceCid = createNumber("workspaceCid", Long.class);

    public final ComparablePath<java.util.UUID> workspaceId = createComparable("workspaceId", java.util.UUID.class);

    public QWorkSpaceEntity(String variable) {
        this(WorkSpaceEntity.class, forVariable(variable), INITS);
    }

    public QWorkSpaceEntity(Path<? extends WorkSpaceEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWorkSpaceEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWorkSpaceEntity(PathMetadata metadata, PathInits inits) {
        this(WorkSpaceEntity.class, metadata, inits);
    }

    public QWorkSpaceEntity(Class<? extends WorkSpaceEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.likelion.stepstone.user.model.QUserEntity(forProperty("user")) : null;
    }

}

