package com.likelion.stepstone.stepstone.user;

import com.likelion.stepstone.user.UserRepository;
import com.likelion.stepstone.user.UserService;
import com.likelion.stepstone.user.model.UserDto;
import com.likelion.stepstone.user.model.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootTest
public class UserControllerTest {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
    public void addUserTest1(){


        UserDto user1 = UserDto.builder()
                .userId("user1")
                .password(passwordEncoder.encode("qwer1234"))
                .name("일반이지현")
                .role("ROLE_USER")
                .build();
        userService.createUser(user1);


        UserDto user2 = UserDto.builder()
                .userId("user2")
                .password(passwordEncoder.encode("qwer1234"))
                .name("일반이지현2")
                .role("ROLE_USER")
                .build();
        userService.createUser(user2);


        UserDto user3 = UserDto.builder()
                .userId("devel1")
                .password(passwordEncoder.encode("qwer1234"))
                .name("개발이지현")
                .role("ROLE_DEVELOPER")
                .build();
        userService.createUser(user3);


        UserDto user4 = UserDto.builder()
                .userId("devel2")
                .password(passwordEncoder.encode("qwer1234"))
                .name("개발이지현2")
                .role("ROLE_DEVELOPER")
                .build();
        userService.createUser(user4);



    }

}
