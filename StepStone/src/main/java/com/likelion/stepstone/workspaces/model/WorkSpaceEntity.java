package com.likelion.stepstone.workspaces.model;

import com.likelion.stepstone.user.model.UserDto;
import com.likelion.stepstone.user.model.UserEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "workspaces")
public class WorkSpaceEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workspace_cid")
    @Setter
    private Long workspaceCid;

    @Type(type = "uuid-char")
    @Column(name = "workspace_id")
    private UUID workspaceId;

    @Column(name = "post_cid")
    @Setter
    private Long postCid;

    @Column(name = "title")
    private String title;


    @Column(name = "body",columnDefinition = "TEXT")
    private String body;

    @ManyToOne
    private UserEntity user;

    @Setter
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Setter
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;



    public static WorkSpaceEntity toEntity(WorkSpaceDto dto) {
        WorkSpaceEntity entity = WorkSpaceEntity.builder()
                .workspaceCid(dto.getWorkspaceCid())
                .workspaceId(dto.getWorkspaceId())
                .postCid(dto.getPostCid())
                .title(dto.getTitle())
                .body(dto.getBody())
                .user(dto.getUser())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();

        return entity;
    }

}