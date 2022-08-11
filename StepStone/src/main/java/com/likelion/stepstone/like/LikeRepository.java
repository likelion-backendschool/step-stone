package com.likelion.stepstone.like;


import com.likelion.stepstone.like.model.LikeDto;
import com.likelion.stepstone.like.model.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long> {


    Optional<LikeDto> findByUserId(UUID userId);

    Optional<LikeDto> findByPostId(UUID postId);

    void deleteByUserIdAndPostId(UUID userId,UUID postId);
}
