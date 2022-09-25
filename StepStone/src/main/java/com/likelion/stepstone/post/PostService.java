package com.likelion.stepstone.post;


import com.likelion.stepstone.authentication.PrincipalDetails;
import com.likelion.stepstone.like.LikeRepository;
import com.likelion.stepstone.like.model.LikeEntity;
import com.likelion.stepstone.post.model.PostDto;
import com.likelion.stepstone.post.model.PostEntity;
import com.likelion.stepstone.post.model.PostVo;
import com.likelion.stepstone.user.model.UserDto;
import com.likelion.stepstone.user.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


public class PostService {
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    public PostService(PostRepository postRepository,LikeRepository likeRepository) {
        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
    }
    public void create(PostDto postDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        PostEntity postEntity = PostEntity.toEntity(postDto);
        UserEntity userEntity = principalDetails.getUser();

        postEntity.setUser(userEntity);
        postEntity.setPostId(UUID.randomUUID());
        postEntity.setLikes(0);
        postEntity.setUpdatedAt(LocalDateTime.now());

        postRepository.save(postEntity);

    }


    @PreAuthorize("hasRole('ROLE_ADMIN') or #postDto.user.userId == authentication.principal.username")
    public void delete(PostDto postDto) {
        PostEntity postEntity = postRepository.findByPostCid(postDto.getPostCid());
        postRepository.delete(postEntity);

        List<LikeEntity> likeEntities = likeRepository.findByPostCid(postDto.getPostCid());
        for(LikeEntity likeEntity:likeEntities){
            likeRepository.delete(likeEntity);}
    }
    private Pageable getPageable(int page, int size, Sort DESC) {
        Pageable pageable = PageRequest.of(page, size, DESC);
        return pageable;
    }

    public List<PostVo> getSortedPostList() {
        List<PostEntity> postEntities = postRepository.findAll();

        List<PostVo> postVoList = postEntities.stream()
                .sorted(Comparator.comparing(PostEntity::getLikes).reversed())
                .map(postEntity -> PostVo.toVo(PostDto.toDto(postEntity)))
                .collect(Collectors.toList());

        return postVoList;
    }

    public Page<PostEntity> getMyPostList(int page, Long userCid) {
        Pageable pageable = getPageable(page, 5, Sort.by(Sort.Direction.DESC, "postCid"));
        return postRepository.findAllByUserUserCid(userCid, pageable);
    }

    public Page<PostEntity> getList(int page, PrincipalDetails principalDetails) {

        if (principalDetails != null) {
            UserEntity userEntity = principalDetails.getUser();
            List<PostEntity> checkedPostEnties = postRepository.getPostEntitiy(userEntity);
            for (PostEntity checkedPostEntity : checkedPostEnties) {
                checkedPostEntity.setChecked(true);
            }
        }

        Pageable pageable = getPageable(page, 5, Sort.by(Sort.Direction.DESC, "updatedAt"));
        return postRepository.findAll(pageable);
    }

    public PostDto getPostDto(long postCid) {
        PostDto postDto = PostDto.toDto(postRepository.findByPostCid(postCid));
        return postDto;
    }

    public void modify(PostDto postDto, String title, String body) {
        PostEntity postEntity = postRepository.findByPostCid(postDto.getPostCid());

        postEntity.setTitle(title);
        postEntity.setBody(body);
//      postEntity.setUpdatedAt(LocalDateTime.now());

        postRepository.save(postEntity);
    }

    public void postUp(PostDto postDto) {
        PostEntity postEntity = postRepository.findByPostCid(postDto.getPostCid());
        postEntity.setUpdatedAt(LocalDateTime.now());
        postRepository.save(postEntity);
    }


    //region ChoYeonJun Add
    public PostEntity findByPostCid(Long postCid){
        return postRepository.findByPostCid(postCid);
    }

    //endregion
}