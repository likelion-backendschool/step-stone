package com.likelion.stepstone.post;

import com.likelion.stepstone.post.model.PostDto;
import com.likelion.stepstone.post.model.PostEntity;
import com.likelion.stepstone.post.model.PostVo;
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

    public void create(PostDto postDto, UserEntity user) {
        PostEntity postEntity = PostEntity.toEntity(postDto);
        postEntity.setPostId(UUID.randomUUID());
        postEntity.setLikes(0);
        postEntity.setUser(user);

        postRepository.save(postEntity);

    }
    // 이슈게시글에 넘길 리스트
    public List<PostVo> getPostList() {
        List<PostEntity> postEntities = postRepository.findAll();
        return postEntities.stream().map(postEntity -> PostVo.toVo(PostDto.toDto(postEntity))).collect(Collectors.toList());
    }

    public Page<PostEntity> getList(int page) {
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

    public PostEntity getPostEntity(long postCid) {
        return postRepository.findByPostCid(postCid);
    }
    public void delete(PostEntity postEntity) {postRepository.delete(postEntity);}

    public void modify(PostEntity postEntity, String title, String body) {
        postEntity.setTitle(title);
        postEntity.setBody(body);
        postEntity.setUpdatedAt(LocalDateTime.now());
        postRepository.save(postEntity);
    }

}