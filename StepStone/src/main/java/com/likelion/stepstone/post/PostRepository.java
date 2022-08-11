package com.likelion.stepstone.post;

import com.likelion.stepstone.post.model.PostEntity;
import com.likelion.stepstone.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {


    PostEntity findByTitle(String title);
}
