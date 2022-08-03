package comlikelion.stepstone.post;

import comlikelion.stepstone.post.model.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
