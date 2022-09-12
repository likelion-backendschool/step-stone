package com.likelion.stepstone.post;

import com.likelion.stepstone.post.model.PostDto;
import com.likelion.stepstone.post.model.PostEntity;
import com.likelion.stepstone.post.model.PostVo;
import com.likelion.stepstone.workspaces.model.WorkSpaceDto;
import com.likelion.stepstone.workspaces.model.WorkSpaceEntity;
import com.likelion.stepstone.workspaces.model.WorkSpaceVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;
import java.util.stream.Collectors;

public class PostService {
    private final PostRepository postRepository;


    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

//    public List<PostEntity> postList() {
//
//    }

    public void create(PostDto postDto) {
        PostEntity postEntity = PostEntity.toEntity(postDto);
        postEntity.setPostId(UUID.randomUUID());
        postEntity.setLikes(0);
        postEntity.setUserCid(1L);

        postRepository.save(postEntity);
    }


    public void update(PostDto postDto) {
        PostEntity postEntity = postRepository.findByPostCid(postDto.getPostCid());
        postEntity.setTitle(postDto.getTitle());
        postEntity.setBody(postDto.getBody());
        postRepository.save(postEntity);


    }

    public void delete(PostDto postDto) {
        PostEntity postEntity = postRepository.findByPostCid(postDto.getPostCid());
        postRepository.delete(postEntity);
    }

    public Page<PostDto> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return postRepository.findAll(pageable).map(post -> PostDto.toDto(post));
    }

    public PostEntity getPost(Long postCid) {
            return postRepository.findByPostCid(postCid);

    }

    public Page<PostEntity> getPostList(int page) {
        Pageable pageable = getPageable(page, 5, Sort.by(Sort.Direction.DESC, "postCid"));
        return postRepository.findAll(pageable);
    }

    private Pageable getPageable(int page, int size, Sort DESC) {
        Pageable pageable = PageRequest.of(page, size, DESC);
        return pageable;
    }


    public List<PostVo> getPostList() {
        List<PostEntity> postEntities = postRepository.findAll();
        return postEntities.stream().map(postEntity -> PostVo.toVo(PostDto.toDto(postEntity))).collect(Collectors.toList());
    }


    public List<PostVo> getSortedPostList() {
        List<PostEntity> postEntities = postRepository.findAll();

        List<PostVo> postVoList = postEntities.stream()
                .sorted(Comparator.comparing(PostEntity::getLikes).reversed())
                .map(postEntity -> PostVo.toVo(PostDto.toDto(postEntity)))
                .collect(Collectors.toList());

        return postVoList;
    }

    // TODO : Post 작성 시에도 로그인 정보 할당 수정해서 getUserPostList 완성하기
    /*
    public List<PostDto> getUserPostList(Long userCid) {
        List<PostEntity> postEntities = postRepository.findAll();

        List<PostDto> postDtos = postEntities.stream()
                .filter(workSpaceEntity -> workSpaceEntity.getUser().getUserCid().equals(userCid))
                .map(workSpaceEntity -> WorkSpaceVo.toVo(WorkSpaceDto.toDto(workSpaceEntity)))
                .collect(Collectors.toList());

        return workSpaceVos;
    }
     */
}