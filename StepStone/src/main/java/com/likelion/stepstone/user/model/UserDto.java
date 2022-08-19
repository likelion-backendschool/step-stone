package com.likelion.stepstone.user.model;

import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@ToString
@Accessors(chain=true)
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long userCid;

    private String userId;

    @Setter
    private String name;

    @Setter
    private String password;

    @Setter
    private String role;

    private LocalDateTime createdAt;

    @Setter
    private LocalDateTime updatedAt;

    public static UserDto toDto(UserEntity entity) {
        UserDto dto = UserDto.builder()
                .userCid(entity.getUserCid())
                .userId(entity.getUserId())
                .name(entity.getName())
                .password(entity.getPassword())
                .role(entity.getRole())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();

        return dto;
    }
}
