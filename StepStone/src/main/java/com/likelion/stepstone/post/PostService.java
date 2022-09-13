package com.likelion.stepstone.post;


import com.likelion.stepstone.post.model.PostDto;
import com.likelion.stepstone.post.model.PostEntity;
import com.likelion.stepstone.post.model.PostVo;
import com.likelion.stepstone.user.model.UserDto;
import com.likelion.stepstone.user.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void create(PostDto postDto, UserDto userDto) {
        PostEntity postEntity = PostEntity.toEntity(postDto);
        UserEntity user = UserEntity.toEntity(userDto);

        postEntity.setUser(user);
        postEntity.setPostId(UUID.randomUUID());
        postEntity.setLikes(0);

        postRepository.save(postEntity);

    }
    // 이슈게시글에 넘길 리스트
    public List<PostVo> getPostList() {
        List<PostEntity> postEntities = postRepository.findAll();
        return postEntities.stream().map(postEntity -> PostVo.toVo(PostDto.toDto(postEntity))).collect(Collectors.toList());
    }

    public Page<PostEntity> getList(int page, UserDto user) {
        /*
        * 레프트조인 했을 때 레프트 기준으로 해서
        * 왼쪽 테이블값만 만족하는 값들만 나옴 > 만족 안 하는건 null
        * 좋아요를 안 한 포스트도 나와야 하니까 > 일단 좋아요 안 해도 리스트엔 나타내줘야 하 ㅁ
        * 그냥 조인하면 널값이 없이 걍 안 나옴 > 리스트에 안 나옴 , 좋아요한 게시글만 나옴
        *
        *이너는 걍
        * 조인 조건을 둘 다 만족할때만 나옴
        * 1      :       N
        * post leftjoin like, user = login user
        * postDto checkedPost true로 변경
        */

        //에러남.. 수정 해야함.. 쿼리 천재의 길 실패..
        List<PostEntity> checkedPostEnties = postRepository.getPostEntitiy();
        for(PostEntity checkedPostEntity:checkedPostEnties){
           if(checkedPostEntity.getUser().getUserCid() == user.getUserCid()){
               checkedPostEntity.setChecked(true);
           }
        }

        Pageable pageable = getPageable(page, 5, Sort.by(Sort.Direction.DESC, "postCid"));
        return postRepository.findAll(pageable);
    }

    private Pageable getPageable(int page, int size, Sort DESC) {
        Pageable pageable = PageRequest.of(page, size, DESC);
        return pageable;
    }

/*  게시글 정렬을 해야되나?
    public Page<PostDto> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return postRepository.findAll(pageable).map(post -> PostDto.toDto(post));
    }
*/

    public PostDto getPostDto(long postCid) {
        PostDto postDto = PostDto.toDto(postRepository.findByPostCid(postCid));
        return postDto;
    }
    public void delete(PostDto postDto) {
        PostEntity postEntity = postRepository.findByPostCid(postDto.getPostCid());

        postRepository.delete(postEntity);}

    public void modify(PostDto postDto, String title, String body) {
        PostEntity postEntity = postRepository.findByPostCid(postDto.getPostCid());

        postEntity.setTitle(title);
        postEntity.setBody(body);
        postEntity.setUpdatedAt(LocalDateTime.now());

        postRepository.save(postEntity);
    }

}