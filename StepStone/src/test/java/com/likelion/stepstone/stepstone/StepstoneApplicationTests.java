package com.likelion.stepstone.stepstone;

import com.likelion.stepstone.like.LikeRepository;
import com.likelion.stepstone.like.model.LikeEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootTest
class StepstoneApplicationTests {

	@Autowired
	private LikeRepository likeRepository;
	@Test
	void contextLoads() {
	}


	@Test
	void likeRepositorySave(){

		UUID postId = UUID.randomUUID();
		UUID userId = UUID.randomUUID();

		LikeEntity q1 = new LikeEntity();
		q1.setPostId(postId);
		q1.setUserId(userId);

		likeRepository.save(q1);


	}
}

