package com.likelion.stepstone.like.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@ToString
public class LikeVo {

    /**
     * Vo 모델 클래스는 변경 하지 않는다.
     * Value Object
     */

    private final Long likeId;

    private final Long userCid;

    private final Long postCid;

    private final LocalDateTime createdAt;


    public static LikeVo toVo(LikeDto dto) {
        LikeVo vo = LikeVo.builder()
                .likeId(dto.getLikeId())
                .userCid(dto.getUserCid())
                .postCid(dto.getPostCid())
                .createdAt(dto.getCreatedAt())
                .build();

        return vo;
    }
}