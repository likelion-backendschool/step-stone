package com.likelion.stepstone.like;


import com.likelion.stepstone.like.model.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;



public interface LikeRepository extends JpaRepository<LikeEntity, Long> {




    @Transactional
    void deleteByPostCidAndUserCid(Long postCid, Long userCid);

    Optional<LikeEntity> findByPostCidAndUserCid(Long postCid, Long userCid);
//    Optional<LikeEntity> findByPostCidAndUserCid( Long userCid);



    List<LikeEntity> findByPostCid(Long postCid);
}
