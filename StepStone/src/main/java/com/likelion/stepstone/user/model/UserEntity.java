package com.likelion.stepstone.user.model;

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
@ToString
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_cid")
    private Long userCid;

    //사용자Id를 해싱한다.
    @Column(name = "user_id")
    private String userId;

    @Setter
    @Column(name = "name")
    private String name; // 실명

    @Setter
    @Column(name = "password")
    private String password;

    @Setter
    @Column(name = "role")
    private String role;

    @Setter
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Setter
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    public static UserEntity toEntity(UserDto dto) {
        UserEntity entity = UserEntity.builder()
                .userCid(dto.getUserCid())
                .userId(dto.getUserId())
                .name(dto.getName())
                .password(dto.getPassword())
                .role(dto.getRole())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();

        return entity;
    }
}
