package com.likelion.stepstone.user;

import com.likelion.stepstone.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
