package com.likelion.stepstone.like.model;


import com.likelion.stepstone.user.model.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "likes")
public class LikeEntity {

    /**
     * Entity 는 Database like 테이블과 연결한다.
     * Entity 는 Repository 에서만 사용한다.
     */

    @Id// PK 임을 나타낸다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키 생성을 데이터베이스에 위임한다.
    // null 값이 넘어와도 AUTO_INCREMENT를 사용하여 기본키를 생성한다.
    private Long likeId;

//    @Setter
//    @Column(name = "user_cid")
//    private Long userCid;

    @Setter
    @ManyToOne
    private UserEntity user;

    @Setter
    @Column(name = "post_cid")
    private Long postCid;

    @Setter
    @Column(name = "created_at")
    private LocalDateTime createdAt;



    public static LikeEntity toEntity(LikeDto dto) {
        LikeEntity entity = LikeEntity.builder()
                .likeId(dto.getLikeId())
//                .userCid(dto.getUserCid())
                .user(dto.getUser())
                .postCid(dto.getPostCid())
                .createdAt(dto.getCreatedAt())
                .build();

        return entity;
    }
}
