package com.likelion.stepstone.user.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class UserVo {

    private final Long userCid;

    private final String userId;

    private final String name;

    private final String password;

    private final String role;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    public static UserVo toVo(UserDto dto) {
        UserVo toVo = UserVo.builder()
                .userCid(dto.getUserCid())
                .userId(dto.getUserId())
                .name(dto.getName())
                .password(dto.getPassword())
                .role(dto.getRole())
                .build();

        return toVo;
    }
}
