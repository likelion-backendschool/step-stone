package com.likelion.stepstone.projects.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProjectEntity is a Querydsl query type for ProjectEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProjectEntity extends EntityPathBase<ProjectEntity> {

    private static final long serialVersionUID = -1803752666L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProjectEntity projectEntity = new QProjectEntity("projectEntity");

    public final StringPath body = createString("body");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> postCid = createNumber("postCid", Long.class);

    public final NumberPath<Long> projectCid = createNumber("projectCid", Long.class);

    public final ComparablePath<java.util.UUID> projectId = createComparable("projectId", java.util.UUID.class);

    public final StringPath title = createString("title");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final com.likelion.stepstone.user.model.QUserEntity user;

    public final NumberPath<Long> workspaceCid = createNumber("workspaceCid", Long.class);

    public QProjectEntity(String variable) {
        this(ProjectEntity.class, forVariable(variable), INITS);
    }

    public QProjectEntity(Path<? extends ProjectEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProjectEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProjectEntity(PathMetadata metadata, PathInits inits) {
        this(ProjectEntity.class, metadata, inits);
    }

    public QProjectEntity(Class<? extends ProjectEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.likelion.stepstone.user.model.QUserEntity(forProperty("user")) : null;
    }

}

