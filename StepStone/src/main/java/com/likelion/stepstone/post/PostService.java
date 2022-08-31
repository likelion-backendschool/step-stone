package com.likelion.stepstone.post;

import com.likelion.stepstone.post.model.PostDto;
import com.likelion.stepstone.post.model.PostEntity;
import com.likelion.stepstone.post.model.PostVo;
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

    public List<PostVo> getPostList1() {
        List<PostEntity> postEntities = postRepository.findAll();

        List<PostVo> postVoList = postEntities.stream()
                .map(postEntity -> PostVo.toVo(PostDto.toDto(postEntity)))
                .sorted(Comparator.comparing(PostVo::getLikes))
                .collect(Collectors.toList());

        List<PostVo> postVoList1 = new ArrayList<>(postVoList.subList(0, 3));

        return postVoList1;
    }

    public List<PostVo> getPostList2() {
        List<PostEntity> postEntities = postRepository.findAll();

        List<PostVo> postVoList = postEntities.stream()
                .map(postEntity -> PostVo.toVo(PostDto.toDto(postEntity)))
                .sorted(Comparator.comparing(PostVo::getLikes))
                .collect(Collectors.toList());

        List<PostVo> postVoList2 = new ArrayList<>(postVoList.subList(3, 6));

        return postVoList2;
    }

    public List<PostVo> getPostList3() {
        List<PostEntity> postEntities = postRepository.findAll();

        List<PostVo> postVoList = postEntities.stream()
                .map(postEntity -> PostVo.toVo(PostDto.toDto(postEntity)))
                .sorted(Comparator.comparing(PostVo::getLikes))
                .collect(Collectors.toList());

        List<PostVo> postVoList3 = new ArrayList<>(postVoList.subList(6, 9));

        return postVoList3;
    }

}