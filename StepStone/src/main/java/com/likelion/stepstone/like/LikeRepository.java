package com.likelion.stepstone.like;

import com.likelion.stepstone.like.model.LikeEntity;
import com.likelion.stepstone.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


public interface LikeRepository extends JpaRepository<LikeEntity, Long> {

    @Transactional
    void deleteByPostCidAndUser(Long postCid, UserEntity user);

    Optional<LikeEntity> findByPostCidAndUser(Long postCid, UserEntity user);
//    Optional<LikeEntity> findByPostCidAndUserCid( Long userCid);
    List<LikeEntity> findByPostCid(Long postCid);

    List<LikeEntity> findByUser(UserEntity user);
}
