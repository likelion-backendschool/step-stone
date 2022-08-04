package comlikelion.stepstone.user;

import comlikelion.stepstone.post.model.PostEntity;
import comlikelion.stepstone.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
