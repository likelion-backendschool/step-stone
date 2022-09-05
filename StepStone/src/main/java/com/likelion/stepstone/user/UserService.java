package com.likelion.stepstone.user;

import com.likelion.stepstone.user.model.LoginVo;
import com.likelion.stepstone.user.model.UserDto;
import com.likelion.stepstone.user.model.UserEntity;
import com.likelion.stepstone.user.model.UserVo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void createUser(UserDto userDto) {
        UserEntity userEntity = UserEntity.toEntity(userDto);

        userEntity.setUserId(userDto.getUserId());
        userEntity.setPassword(userDto.getPassword());
        userEntity.setName(userDto.getName());
        userEntity.setRole(userDto.getRole());

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
                    .role(userEntity.getRole())
                    .createdAt(userEntity.getCreatedAt())
                    .updatedAt(userEntity.getUpdatedAt())
                    .build();

            userDtoList.add(userDTO);
        }

        return userDtoList;
    }

    @Transactional
    public void deleteUser(String userId) {
        userRepository.deleteByUserId(userId);
    }

    public void updateUser(UserDto userDto) {
        Optional<UserEntity> userEntities = userRepository.findByUserId(userDto.getUserId());
        UserEntity userEntity = userEntities.get();

        userEntity.setPassword(userDto.getPassword());
        userEntity.setName(userDto.getName());

        userRepository.save(userEntity);
    }

    public void join(LoginVo user) {
        UserEntity userEntity = UserEntity.toEntity(user);
        userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
        userRepository.save(userEntity);
    }

    public void save(UserEntity userEntity) {
        userRepository.save(userEntity);
    }
<<<<<<< HEAD
=======

>>>>>>> e45bb35b4dd841dbfb4b4b0531e4543e9f0a495c
}
