package com.likelion.stepstone.post;


import com.likelion.stepstone.like.model.QLikeEntity;
import com.likelion.stepstone.post.model.PostEntity;
import com.likelion.stepstone.post.model.QPostEntity;

import com.likelion.stepstone.user.model.UserDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.likelion.stepstone.like.model.QLikeEntity.likeEntity;



@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;




    @Override
    public List<PostEntity> getPostEntitiy() {
        //이렇게 하면 안 됨.. user정보 추가 하던가 쿼리 수정 필요..

        List<PostEntity> postEntities = jpaQueryFactory
                .select(QPostEntity.postEntity)
                .from(QPostEntity.postEntity)
                .leftJoin(likeEntity)
                .where(QPostEntity.postEntity.postCid.eq(likeEntity.postCid))
                .fetch();

        return postEntities;



    }

}