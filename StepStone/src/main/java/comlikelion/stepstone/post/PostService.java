package comlikelion.stepstone.post;

import comlikelion.stepstone.post.model.PostDto;
import comlikelion.stepstone.post.model.PostEntity;

import java.util.UUID;

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
}
