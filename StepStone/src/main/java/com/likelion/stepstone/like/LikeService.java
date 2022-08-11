package com.likelion.stepstone.like;

import com.likelion.stepstone.like.model.LikeDto;
import com.likelion.stepstone.like.model.LikeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
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
    public void idCheck(UUID postId, UUID userId){
        //  <LikeEntity> 가 되어야 하는거 아닌가?
       Optional<LikeDto> likes = likeRepository.findByUserId(userId);
     if(likes.isPresent()) {
         Optional<LikeDto> liked = likeRepository.findByPostId(postId);;
         if(liked.isPresent()){
             likeRepository.deleteByUserIdAndPostId(userId,postId);
         }else{ like(postId,userId);}
           }else{
          likeRepository.deleteByUserIdAndPostId(userId,postId);
       }
    }
//   arrert~는 Test할 때만 사용 가능한건가?
    public void like(UUID postId, UUID userId) {

        LikeEntity likeEnti = new LikeEntity();
        likeEnti.setUserId(userId);
        likeEnti.setPostId(postId);
        likeEnti.setCreatedAt(LocalDateTime.now());
        likeRepository.save(likeEnti);
    }
    public void deletelikes(LikeDto likeDto) {

        LikeEntity likeEntity = LikeEntity.toEntity(likeDto);
        likeRepository.delete(likeEntity);
    }

}
