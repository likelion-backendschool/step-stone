package com.likelion.stepstone.post.model;


import com.likelion.stepstone.user.model.UserDto;
import com.likelion.stepstone.user.model.UserEntity;
import lombok.*;

import javax.persistence.Column;
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

//    @Setter
//    private long userCid;

    @Setter
    private UserEntity user;

    @Setter
    private boolean checked;

    @Setter
    private LocalDateTime createdAt;

    @Setter
    private LocalDateTime updatedAt;

    public static PostDto toDto(PostEntity entity) {
        PostDto dto = PostDto.builder()
                .postCid(entity.getPostCid())
                .postCid(entity.getPostCid())
                .user(entity.getUser())
                .checked(false)
                .title(entity.getTitle())
                .body(entity.getBody())
                .likes(entity.getLikes())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();

        return dto;
    }

}

