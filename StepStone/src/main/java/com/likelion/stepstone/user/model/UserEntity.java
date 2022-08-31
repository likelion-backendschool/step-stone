package com.likelion.stepstone.user.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    @Setter
    @Column(name = "login_before")
    private boolean loginBefore;

    public List<String> getRoleList(){
        if(this.role.length() > 0){
            return Arrays.asList(this.role.split(","));
        }
        return new ArrayList<>(); // null 을 리턴 안해주기 위함.
    }


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

    public static UserEntity toEntity(LoginVo vo) {
        UserEntity entity = UserEntity.builder()
                .userId(vo.getUserId())
                .name(vo.getName())
                .password(vo.getPassword())
                .role(vo.getRole())
                .build();
        return entity;
    }
}
