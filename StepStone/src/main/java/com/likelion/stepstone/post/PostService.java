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

        List<PostEntity> checkedPostEnties = postRepository.getPostEntitiy(user);
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