package com.likelion.stepstone.post;

import com.likelion.stepstone.post.model.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long>, PostRepositoryCustom {
    Page<PostEntity> findAll(Pageable pageable);
    PostEntity findByPostCid(Long postCid);

    Page<PostEntity> findAllByUserUserCid(Long userCid, Pageable pageable);
}
