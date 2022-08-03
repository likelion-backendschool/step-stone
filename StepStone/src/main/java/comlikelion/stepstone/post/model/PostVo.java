package comlikelion.stepstone.post.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;
@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PostVo {

    /**
     * Vo 모델 클래스는 변경 하지 않는다.
     * Visual Object
     */

    private UUID postId;

    private String title;

    private String body;

    private Integer likes;

    private UUID userId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static PostVo toVo(PostEntity entity) {
        PostVo vo = PostVo.builder()
                .postId(entity.getPostId())
                .userId(entity.getUserId())
                .body(entity.getBody())
                .likes(entity.getLikes())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();

        return vo;
    }
}
