package com.likelion.stepstone.post;

import com.likelion.stepstone.post.model.PostEntity;
import com.likelion.stepstone.user.model.UserEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import java.util.List;

import static com.likelion.stepstone.like.model.QLikeEntity.likeEntity;
import static com.likelion.stepstone.post.model.QPostEntity.postEntity;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<PostEntity> getPostEntitiy(UserEntity userEntity) {

        List<PostEntity> postEntities = jpaQueryFactory
                .selectFrom(postEntity)
                .leftJoin(likeEntity)
                .on(postEntity.postCid.eq(likeEntity.postCid))   // 조인한 테이블에서 선택하기
                .where(likeEntity.user.userId.eq(userEntity.getUserId()))
                .fetch();

        return postEntities;
    }

}