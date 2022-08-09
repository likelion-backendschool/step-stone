package com.likelion.stepstone.like;


import com.likelion.stepstone.like.model.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
}
