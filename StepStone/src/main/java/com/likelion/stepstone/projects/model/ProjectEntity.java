package com.likelion.stepstone.projects.model;

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
@Table(name = "projects")
public class ProjectEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_cid")
    @Setter
    private Long projectCid;

    @Type(type = "uuid-char")
    @Column(name = "project_id")
    private UUID projectId;


    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;


    @Column(name = "user_cid")
    private Long userCid;

    @Setter
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Setter
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;



    public static ProjectEntity toEntity(ProjectDto dto) {
        ProjectEntity entity = ProjectEntity.builder()
                .projectId(dto.getProjectId())
                .title(dto.getTitle())
                .body(dto.getBody())
                .userCid(dto.getUserCid())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();

        return entity;
    }

}
