package com.likelion.stepstone.post.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;
@Builder
@Getter
@ToString
public class PostVo {

    /**
     * Vo 모델 클래스는 변경 하지 않는다.
     * Value Object
     */

    private final UUID postId;

    private final String title;

    private final String body;

    private final Integer likes;

    private final UUID userId;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    public static PostVo toVo(PostDto dto) {
        PostVo vo = PostVo.builder()
                .postId(dto.getPostId())
                .userId(dto.getUserId())
                .body(dto.getBody())
                .likes(dto.getLikes())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();

        return vo;
    }
}
