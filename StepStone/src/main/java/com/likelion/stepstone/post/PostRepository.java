package com.likelion.stepstone.post;

import com.likelion.stepstone.post.model.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface PostRepository extends JpaRepository<PostEntity, Long> {


    PostEntity findByTitle(String title);


    PostEntity findByPostCid(Long i);

    PostEntity findByPostId(UUID postId);
}
