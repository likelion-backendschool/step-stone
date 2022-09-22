package com.likelion.stepstone.projects.model;

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
public class ProjectVo {

    private Long projectCid;

    private UUID projectId;

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


    public static ProjectDto toVo(ProjectDto dto) {
        ProjectVo vo = ProjectVo.builder()
                .projectCid(dto.getProjectCid())
                .postCid(dto.getPostCid())
                .projectId(dto.getProjectId())
                .title(dto.getTitle())
                .body(dto.getBody())
                .user(dto.getUser())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();

        return dto;
    }
}
