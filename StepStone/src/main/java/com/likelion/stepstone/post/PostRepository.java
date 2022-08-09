package com.likelion.stepstone.post;

import com.likelion.stepstone.post.model.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
  Page<PostEntity> findAll(Pageable pageable);
}