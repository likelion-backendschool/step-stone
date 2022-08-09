package comlikelion.stepstone.post;

import comlikelion.stepstone.post.model.PostDto;
import comlikelion.stepstone.post.model.PostVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PostService {
  private final PostRepository postRepository;

  public PostService(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  public void create(PostDto postDto) {
    PostEntity postEntity = PostEntity.toEntity(postDto);
    postEntity.setPostId(UUID.randomUUID());
//        postEntity.setLikes(0);

    postRepository.save(postEntity);
  }

  public Page<PostVo> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
    Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();

    Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
    return postRepository.findAll(pageable).map(post -> PostVo.toVo(PostDto.toDto(post)));
  }
}
