package comlikelion.stepstone.stepstone.like.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikeVo {

    /**
     * Vo 모델 클래스는 변경 하지 않는다.
     * Visual Object
     */

    private Long likeId;

    private UUID userId;

    private UUID postId;

    private LocalDateTime createdAt;


    public static LikeVo toVo(LikeEntity entity) {
        LikeVo vo = LikeVo.builder()
                .likeId(entity.getLikeId())
                .userId(entity.getUserId())
                .postId(entity.getPostId())
                .createdAt(entity.getCreatedAt())
                .build();

        return vo;
    }
}
