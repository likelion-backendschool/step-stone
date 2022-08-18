package com.likelion.stepstone.like;


import com.likelion.stepstone.like.model.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface LikeRepository extends JpaRepository<LikeEntity, Long> {



    List<LikeEntity> findByPostId(UUID postId);

    @Transactional
    void deleteByPostIdAndUserId(UUID postId, UUID userId);

    Optional<LikeEntity> findByPostIdAndUserId(UUID postId, UUID userId);
}
