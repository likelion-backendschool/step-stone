package com.likelion.stepstone.user;

import com.likelion.stepstone.post.PostRepository;
import org.springframework.stereotype.Service;


public class UserService {
    private final UserRepository userRepository;
  public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
