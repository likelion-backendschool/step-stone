package com.likelion.stepstone.workspaces.model;


import com.likelion.stepstone.user.model.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@ToString
public class WorkSpaceVo {

    /**
     * Vo 모델 클래스는 변경 하지 않는다.
     * Value Object
     */


    private final Long workspaceCid;

    private UUID workspaceId;

    private Long postCid;

    @Setter
    private String title;

    @Setter
    private String body;

    private final UserEntity user;

    @Setter
    private LocalDateTime createdAt;

    @Setter
    private LocalDateTime updatedAt;

    public static WorkSpaceDto toVo(WorkSpaceDto dto) {
        WorkSpaceVo vo = WorkSpaceVo.builder()
                .workspaceCid(dto.getWorkspaceCid())
                .workspaceId(dto.getWorkspaceId())
                .postCid(dto.getPostCid())
                .title(dto.getTitle())
                .body(dto.getBody())
                .user(dto.getUser())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();

        return dto;
    }
}