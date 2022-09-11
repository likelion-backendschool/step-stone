package com.likelion.stepstone.like;

import com.likelion.stepstone.like.model.LikeEntity;
import com.likelion.stepstone.post.PostRepository;
import com.likelion.stepstone.post.model.PostEntity;
import com.likelion.stepstone.user.model.UserEntity;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class LikeService {
    private LikeRepository likeRepository;
    private PostRepository postRepository;

    public  LikeEntity getLikeEntity(long postCid, UserEntity user) {

        return likeRepository.findByPostCidAndUser(postCid,user)
                .orElse(null);
    }

    public void idCheck2(Long postCid, UserEntity user) {
        Optional<LikeEntity> likes = likeRepository.findByPostCidAndUser(postCid, user); //userId 가 좋아요한 게시글 찾기
        if (likes.isPresent()) {
            deletelikes(postCid, user);
        } else {  // 있으면 좋아요 한 번 더 클릭한 것 -> 삭제
            like(postCid, user);
        }
        updateLikesCount(postCid);  //좋아요 수 업뎃
    }

    public void like(Long postCid, UserEntity user) { // 좋아요 등록 > 테이블에 등록

        LikeEntity likeEnti = new LikeEntity();
        likeEnti.setUser(user);
        likeEnti.setPostCid(postCid);
        likeEnti.setCreatedAt(LocalDateTime.now());
        likeRepository.save(likeEnti);
    }

    public void deletelikes(Long postCid,  UserEntity user) { //좋아요 취소 > 테이블에서 row 삭제
        likeRepository.deleteByPostCidAndUser(postCid, user);
    }

    public void updateLikesCount(Long postCid) {
        // likes 테이블 속 게시글 모두 찾기 (select * from likes where postId = postId) >> 게시글의 총 수 = 좋아요 수
        List<LikeEntity> likes = likeRepository.findByPostCid(postCid);
        PostEntity post = postRepository.findByPostCid(postCid);  // post테이블 속 posdId = postId인 게시글 찾기
        int i = likes.size();
        post.setLikes(i);  //좋아요 수 업뎃
        postRepository.save(post);

    }
}