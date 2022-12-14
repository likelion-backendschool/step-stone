package com.likelion.stepstone.post.model;


import com.likelion.stepstone.user.model.UserEntity;
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
@Table(name = "posts")
public class PostEntity {

    /**
     * Entity 는 Database like 테이블과 연결한다.
     * Entity 는 Repository 에서만 사용한다.
     */
    @Type(type = "uuid-char")
    @Column(name = "post_id")
    @Setter //테스트용
    private UUID postId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_cid")
    @Setter //테스트용
    private Long postCid;

    @Column(name = "title")
    private String title;

    @Column(name = "body",columnDefinition = "TEXT")
    private String body;

    @Column(name = "likes")
    private Integer likes;

    @ManyToOne
    private UserEntity user;

    @Column(name = "checked")
    @Setter
    @Transient
    private boolean checked;

    @Setter
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Setter
//    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    public static PostEntity toEntity(PostDto dto) {
        PostEntity entity = PostEntity.builder()
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
        return entity;
    }
}