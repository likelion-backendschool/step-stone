package com.likelion.stepstone.post.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    /**
     * 데이터 전달 모델 클래스
     * Data Transfer Object
     */
    private UUID postId;

    private Long postCid;

    @Setter
    private String title;

    @Setter
    private String body;

    @Setter
    private Integer likes;

    @Setter
    private long userCid;

    @Setter
    private LocalDateTime createdAt;

    @Setter
    private LocalDateTime updatedAt;


    public static PostDto toDto(PostEntity entity) {
        PostDto dto = PostDto.builder()
                .postId(entity.getPostId())
                .userCid(entity.getUserCid())
                .body(entity.getBody())
                .likes(entity.getLikes())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();

        return dto;
    }

}

