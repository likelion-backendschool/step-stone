package com.likelion.stepstone.like;

import com.likelion.stepstone.authentication.PrincipalDetails;
import com.likelion.stepstone.like.model.LikeDto;
import com.likelion.stepstone.like.model.LikeEntity;
import com.likelion.stepstone.post.PostRepository;
import com.likelion.stepstone.post.model.PostDto;
import com.likelion.stepstone.post.model.PostEntity;
import com.likelion.stepstone.post.model.PostVo;
import com.likelion.stepstone.user.model.UserDto;
import com.likelion.stepstone.user.model.UserEntity;
import com.likelion.stepstone.workspaces.model.WorkSpaceEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
public class LikeService {
    private LikeRepository likeRepository;
    private PostRepository postRepository;

    public LikeDto getLikeDto(long postCid, PrincipalDetails principalDetails) {

        LikeEntity likeEntity;

        if (principalDetails == null) {
            likeEntity = null;
        } else {
            UserEntity userEntity = principalDetails.getUser();
            likeEntity = likeRepository.findByPostCidAndUser(postCid, userEntity)
                    .orElse(null);
        }

        LikeDto likeDto;

        if (likeEntity != null) {
            likeDto = LikeDto.toDto(likeEntity);
        } else {
            likeDto = null;
        }

        return likeDto;

    }

    public void idCheck2(Long postCid, PrincipalDetails principalDetails) {
        UserEntity userEntity = principalDetails.getUser();
        Optional<LikeEntity> likes = likeRepository.findByPostCidAndUser(postCid, userEntity); //userId 가 좋아요한 게시글 찾기
        if (likes.isPresent()) {
            deletelikes(postCid, principalDetails);
        } else {  // 있으면 좋아요 한 번 더 클릭한 것 -> 삭제
            like(postCid, principalDetails);
        }
        updateLikesCount(postCid);  //좋아요 수 업뎃
    }

    public void like(Long postCid, PrincipalDetails principalDetails) { // 좋아요 등록 > 테이블에 등록
        UserEntity userEntity = principalDetails.getUser();
        LikeEntity likeEnti = new LikeEntity();
        likeEnti.setUser(userEntity);
        likeEnti.setPostCid(postCid);
        likeEnti.setCreatedAt(LocalDateTime.now());
        likeRepository.save(likeEnti);
    }

    public void deletelikes(Long postCid, PrincipalDetails principalDetails) { //좋아요 취소 > 테이블에서 row 삭제
        UserEntity userEntity = principalDetails.getUser();
        likeRepository.deleteByPostCidAndUser(postCid, userEntity);
    }

    public void updateLikesCount(Long postCid) {
        // likes 테이블 속 게시글 모두 찾기 (select * from likes where postId = postId) >> 게시글의 총 수 = 좋아요 수
        List<LikeEntity> likes = likeRepository.findByPostCid(postCid);
        PostEntity post = postRepository.findByPostCid(postCid);  // post테이블 속 posdId = postId인 게시글 찾기
        int i = likes.size();
        post.setLikes(i);  //좋아요 수 업뎃
        postRepository.save(post);

    }

    public List<LikeEntity> getLikeEntity(UserDto userDto) {
        UserEntity user = UserEntity.toEntity(userDto);
        return likeRepository.findByUser(user);
    }

    public List<PostEntity> getLikedPost(List<LikeEntity> likeEntities) {
        List<PostEntity> likedPostEntities = new ArrayList<>();

        for (int i = 0; i < likeEntities.size(); i++) {
            Long postCid = likeEntities.get(i).getPostCid();
            likedPostEntities.add(postRepository.findByPostCid(postCid));
        }

        return likedPostEntities;
    }

}