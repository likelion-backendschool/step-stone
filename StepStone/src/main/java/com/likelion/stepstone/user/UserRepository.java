package com.likelion.stepstone.user;

import com.likelion.stepstone.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity, Long> {

   Optional<UserEntity>  findByName(String name);
}
