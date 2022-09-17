package com.likelion.stepstone.stepstone.user;

import com.likelion.stepstone.user.UserRepository;
import com.likelion.stepstone.user.model.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class UserControllerTest {

    @Autowired
    private UserRepository userRepository;
    @Test
    public void addUserTest1(){
        UserEntity user1 = new UserEntity();
        user1.setUserId("test5");
        user1.setName("테스트5");
        user1.setCreatedAt(LocalDateTime.now());
        user1.setUpdatedAt(LocalDateTime.now());
        user1.setPassword("1234");
        user1.setRole("user");

        userRepository.save(user1);

        UserEntity user2 = new UserEntity();
        user2.setUserId("test6");
        user2.setName("테스트6");
        user2.setCreatedAt(LocalDateTime.now());
        user2.setUpdatedAt(LocalDateTime.now());
        user2.setPassword("1234");
        user2.setRole("user");

        userRepository.save(user2);
    }

}
