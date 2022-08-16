package com.likelion.stepstone.like;

import com.likelion.stepstone.like.model.LikeEntity;
import com.likelion.stepstone.post.PostRepository;
import com.likelion.stepstone.post.model.PostEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class LikeService {
    private LikeRepository likeRepository;
    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }
    private PostRepository postRepository;
    public void idCheck2(UUID postId, UUID userId) {
        List<LikeEntity> likes = likeRepository.findByUserIdAndPostId(postId, userId);  // like테이블에 해당 userId로 등록된 게시글 있나 찾기
        if (likes == null) {  // db에 없으면 등록
            like(postId, userId);
        } else {  // 있으면 좋아요 한 번 더 클릭한 것 -> 테이블에서 삭제하기
            deletelikes(postId, userId);
        }
        updateLikesCount(postId);  //좋아요 수 업뎃
   }
//    public void idCheck(UUID userId, LikeDto likeDto){
//        likes.stream()
//                .filter(likeEntity -> likeEntity.getUserId().equals(userId))
//                .findAny()
//                .orElse(LikeEntity.toEntity(likeDto))
//                .deleteById(userId);
//}
//    likes가 있다 > 포스트아이디랑 일치 한다 > 삭제
//                               안 한다 > 등록
    //           없다                       > 등록
//    public void idCheck(UUID postId, UUID userId){
//       Optional<LikeEntity> likes = likeRepository.findByUserId(userId); //userId 가 좋아요한 게시글 찾기
//     if(likes.isPresent()) {  // 게시글이 있으면
//         Optional<LikeEntity> liked = likeRepository.findByPostId(postId);  //넘어온 게시글번호랑 일치하는 게시글이 있는지 확인
//         if(liked.isPresent()){  // 있으면 좋아요 취소
//             deletelikes(postId, userId);
//         }else{ like(postId,userId);}  //
//           }else{  //좋아요한 게시글이 아예 없어도 좋아요 실행
//         like(postId,userId);
//       }
//    }

    public void like(UUID postId, UUID userId) {

        LikeEntity likeEnti = new LikeEntity();
        likeEnti.setUserId(userId);
        likeEnti.setPostId(postId);
        likeEnti.setCreatedAt(LocalDateTime.now());
        likeRepository.save(likeEnti);
    }

    public void deletelikes(UUID postId, UUID userId) {
        likeRepository.deleteByUserIdAndPostId(postId, userId);
    }

    public void updateLikesCount(UUID postId) {
        // likes 테이블 속 게시글 모두 찾기 (select * from likes where postId = postId) >> 게시글의 총 수 = 좋아요 수
        List<LikeEntity> likes = likeRepository.findByLikeId(32L);
        PostEntity post = postRepository.findByPostId(postId);  // post테이블 속 posdId = postId인 게시글 찾기
        int i = likes.size();
        post.setLikes(i);  //좋아요 수 업뎃
        postRepository.save(post);



    }
}
