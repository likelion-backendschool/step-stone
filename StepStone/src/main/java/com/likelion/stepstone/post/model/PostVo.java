

package com.likelion.stepstone.post.model;

import com.likelion.stepstone.user.model.UserDto;
import com.likelion.stepstone.user.model.UserEntity;
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

    private final Long postCid;

    private final String title;

    private final String body;

    private final Integer likes;

    private final UserEntity user;

    private final boolean checked;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    public static PostVo toVo(PostDto dto) {
        PostVo vo = PostVo.builder()
                .postId(dto.getPostId())
                .postCid(dto.getPostCid())
                .user(dto.getUser())
                .checked(false)
                .title(dto.getTitle())
                .body(dto.getBody())
                .likes(dto.getLikes())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();

        return vo;
    }
}