package com.likelion.stepstone.post;

import com.likelion.stepstone.post.model.PostEntity;
import com.likelion.stepstone.user.model.UserDto;
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
    public List<PostEntity> getPostEntitiy(UserDto user) {

        UserEntity userEntity = UserEntity.toEntity(user);

        List<PostEntity> postEntities = jpaQueryFactory
                .selectFrom(postEntity)
                .leftJoin(likeEntity)
                .on(postEntity.postCid.eq(likeEntity.postCid))
                .where(likeEntity.user.userId.eq(userEntity.getUserId()))
                .fetch();

        return postEntities;

        /*
        * 1. likeEntity.findByUser? where(user.eq(user)) ?
        * 2. postEntity.postCid = likeEntity.postCid  leftjoin
        * 3. 남은 postEntity에 .setchecked(true)
        * */

        /*
         * selset all
         * from A as a
         * leftjoin B as b
         * on a.key=b.key
         */

         /*
          * select all
          * from posts
          * leftjoin likes
          * on posts.postCid = likes.postCid
          * where likes.user.eq(user)
          *
          /

          /*
          QPartsTable qParts = QPartsTable.partsTable;
          QInventoryBalance qBalance = QInventoryBalance.inventoryBalance
          *
          JPAQuery q = new JPAQuery(em);
          q.from(qParts)
          .leftJoin(qParts.inventoryBalance, qBalance)
          .on(qBalance.month.eq(yourMonth).and(qBalance.year.eq(yourYear)))
          .list(qParts);
           */

        /*
          em.persist(new Member("teamA"));
          em.persist(new Member("teamB"));

          List<Tuple> result = queryFactory
                              .select(member, team)
                              .from(member)
                              .leftJoin(team).on(member.username.eq(team.name))
                              .fetch();
         for (Tuple tuple : result) {
      	 System.out.println("t=" + tuple);
      	* */

    }

}