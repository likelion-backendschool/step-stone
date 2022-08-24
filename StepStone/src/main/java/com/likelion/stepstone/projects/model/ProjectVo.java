package com.likelion.stepstone.projects.model;

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

    /**
     * Vo 모델 클래스는 변경 하지 않는다.
     * Value Object
     */



    private UUID projectId;

    @Setter
    private String title;

    @Setter
    private String body;

    @Setter
    private Long userCid;

    @Setter
    private LocalDateTime createdAt;

    @Setter
    private LocalDateTime updatedAt;


    public static ProjectDto toVo(ProjectDto dto) {
        ProjectVo vo = ProjectVo.builder()
                .projectId(dto.getProjectId())
                .title(dto.getTitle())
                .body(dto.getBody())
                .userCid(dto.getUserCid())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();

        return dto;
    }
}
