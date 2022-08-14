package com.likelion.stepstone.like;

import com.likelion.stepstone.like.model.LikeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

//    public void idCheck(UUID userId, LikeDto likeDto){
//        likes.stream()
//                .filter(likeEntity -> likeEntity.getUserId().equals(userId))
//                .findAny()
//                .orElse(LikeEntity.toEntity(likeDto))
//                .deleteById(userId);
//}

    // likes가 있다 > 포스트아이디랑 일치 한다 > 삭제
    //                               안 한다 > 등록
    //         없다                          > 등록
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
//
    public void idCheck(UUID postId, UUID userId) {
        List<LikeEntity> likes = likeRepository.findByUserId(userId);
        if (likes.isEmpty()) {  // db에 없으면 등록
            like(postId, userId);
        } else {
            Optional<LikeEntity> liked = likeRepository.findByPostId(postId);  //넘어온 게시글번호랑 일치하는 게시글이 있는지 확인
            if (liked.isPresent()) {  // 있으면 좋아요 취소
                deletelikes(postId, userId);
            } else {
                like(postId, userId);
            }
        }
    }
    public void like(UUID postId, UUID userId) {

        LikeEntity likeEnti = new LikeEntity();
        likeEnti.setUserId(userId);
        likeEnti.setPostId(postId);
        likeEnti.setCreatedAt(LocalDateTime.now());
        likeRepository.save(likeEnti);
    }

    public void deletelikes(UUID postId, UUID userId) {
        likeRepository.deleteByUserIdAndPostId(userId, postId);
    }

}
