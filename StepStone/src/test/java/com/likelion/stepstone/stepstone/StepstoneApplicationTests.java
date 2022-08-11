package com.likelion.stepstone.stepstone;

import com.likelion.stepstone.like.LikeRepository;
import com.likelion.stepstone.like.LikeService;
import com.likelion.stepstone.like.model.LikeDto;
import com.likelion.stepstone.like.model.LikeEntity;
import com.likelion.stepstone.post.PostRepository;
import com.likelion.stepstone.post.model.PostEntity;
import com.likelion.stepstone.user.UserRepository;
import com.likelion.stepstone.user.model.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootTest
class StepstoneApplicationTests {

	@Autowired
	private LikeRepository likeRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private LikeService likeService;

	@Test
	void contextLoads() {
	}


	@Test
	void likeRepositorySave() {

		UUID postId = UUID.randomUUID();
		UUID userId = UUID.randomUUID();

		LikeEntity q1 = new LikeEntity();
		q1.setPostId(postId);
		q1.setUserId(userId);

		likeRepository.save(q1);


	}

	@Test
	public void like() {
		UserEntity user = userRepository.findByName("qwe");
		PostEntity post = postRepository.findByTitle("title");

		UUID userId = user.getUserId();
		UUID postId = post.getPostId();

		LikeDto likeDto = LikeDto.builder()
				.postId(postId)
				.userId(userId)
				.build();

		likeService.idCheck(postId,userId);


	}
}

