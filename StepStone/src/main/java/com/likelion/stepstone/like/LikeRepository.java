package com.likelion.stepstone.like;


import com.likelion.stepstone.like.model.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long> {


    List<LikeEntity> findByUserId(UUID userId);

    Optional<LikeEntity> findByPostId(UUID postId);

    @Transactional
    void deleteByUserIdAndPostId(UUID userId,UUID postId);
}
