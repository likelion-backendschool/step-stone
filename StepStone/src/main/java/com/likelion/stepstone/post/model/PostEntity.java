package com.likelion.stepstone.post.model;


import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "posts")
public class PostEntity {

    /**
     * Entity 는 Database like 테이블과 연결한다.
     * Entity 는 Repository 에서만 사용한다.
     */

    @Id //PK임
    @Type(type = "uuid-char")
    @Column(name = "post_id")
    private UUID postId;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_cid")
    private Long postCid;

    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    @Column(name = "likes")
    private Integer likes;

    @Type(type = "uuid-char")
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    public static PostEntity toEntity(PostDto dto) {
        PostEntity entity = PostEntity.builder()
                .postId(dto.getPostId())
                .userId(dto.getUserId())
                .title(dto.getTitle())
                .body(dto.getBody())
                .likes(dto.getLikes())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();

        return entity;
    }
}
