package com.likelion.stepstone.user;

import com.likelion.stepstone.user.model.UserDto;
import com.likelion.stepstone.user.model.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(UserDto userDto) {
        UserEntity userEntity = UserEntity.toEntity(userDto);

        userEntity.setUserId(userDto.getUserId());
        userEntity.setPassword(userDto.getPassword());
        userEntity.setName(userDto.getName());

        userRepository.save(userEntity);
    }

    public List<UserDto> getUserlist() {
        List<UserEntity> userEntities = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();

        for( UserEntity userEntity : userEntities) {
            UserDto userDTO = UserDto.builder()
                    .userId(userEntity.getUserId())
                    .password(userEntity.getPassword())
                    .name(userEntity.getName())
                    .createdAt(userEntity.getCreatedAt())
                    .updatedAt(userEntity.getUpdatedAt())
                    .build();

            userDtoList.add(userDTO);
        }

        return userDtoList;
    }


}
