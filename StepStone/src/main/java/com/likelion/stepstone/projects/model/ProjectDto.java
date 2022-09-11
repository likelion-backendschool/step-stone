package com.likelion.stepstone.projects.model;

import com.likelion.stepstone.user.model.UserEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {

    /**
     * 데이터 전달 모델 클래스
     * Data Transfer Object
     */
    private Long projectCid;

    private UUID projectId;

    @Setter
    private String title;

    @Setter
    private String body;

//    @Setter
//    private Long userCid;
    @Setter
    private UserEntity user;

    @Setter
    private LocalDateTime createdAt;

    @Setter
    private LocalDateTime updatedAt;


    public static ProjectDto toDto(ProjectEntity entity) {
        ProjectDto dto = ProjectDto.builder()
                .projectId(entity.getProjectId())
                .title(entity.getTitle())
                .body(entity.getBody())
                .user(entity.getUser())
//                .userCid(entity.getUserCid())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();

        return dto;
    }

}

