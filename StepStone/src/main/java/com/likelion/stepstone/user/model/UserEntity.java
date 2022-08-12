package com.likelion.stepstone.user.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Table(name = "users")
public class UserEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_cid")
    @Setter //테스트용
    private Integer cid;

    //사용자Id를 해싱한다.
    @Id
    @Type(type = "uuid-char")
    @Column(name = "user_id")
    @Setter  //테스트용
    private UUID userId;

    @Setter
    @Column(name = "name")
    private String name; // 실명

    @Setter
    @Type(type = "uuid-char")
    @Column(name = "password")
    private UUID password;

    @Setter
    @Column(name = "role")
    private String role;

    @Setter
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Setter
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    public static UserEntity toEntity(UserDto dto) {
        UserEntity entity = UserEntity.builder()
                .name(dto.getName())
                .password(dto.getPassword())
                .role(dto.getRole())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();

        return entity;
    }


}
