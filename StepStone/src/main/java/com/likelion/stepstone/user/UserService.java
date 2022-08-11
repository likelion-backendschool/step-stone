package com.likelion.stepstone.user;

import com.likelion.stepstone.user.model.UserDto;
import com.likelion.stepstone.user.model.UserEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(UserDto userDto) {
        UserEntity userEntity = UserEntity.toEntity(userDto);

        userEntity.setUserId(UUID.randomUUID());

        userRepository.save(userEntity);
    }
}
