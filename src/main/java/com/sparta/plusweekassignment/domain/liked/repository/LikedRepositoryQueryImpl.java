package com.sparta.plusweekassignment.domain.liked.repository;

import com.querydsl.core.types.Expression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.plusweekassignment.domain.liked.entity.ContentsTypeEnum;
import com.sparta.plusweekassignment.domain.liked.entity.Liked;
import com.sparta.plusweekassignment.domain.liked.entity.QLiked;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LikedRepositoryQueryImpl implements LikedRepositoryQuery {

    private final JPAQueryFactory jpaQueryFactory;

    // select().from() 을 합친게 -> 이거 selectFrom()
    @Override
    public int countByUserIdAndContentsType(Long userId, ContentsTypeEnum contentsType) {
        QLiked liked = QLiked.liked;

        int result = jpaQueryFactory.select(liked)
                .from(liked)
                .where(liked.user.id.eq(userId)
                        .and(liked.contentsType.eq(contentsType)))
                .orderBy(liked.id.asc())
                .fetch().size();

        return result;
    }

    @Override
    public Optional<Liked> findByUserIdAndContentsIdAndContentsType(Long id, Long contentsId, ContentsTypeEnum contentsType) {
        QLiked liked = QLiked.liked;

        Liked result = jpaQueryFactory.select(liked)
                .from(liked)
                .where(liked.user.id.eq(id)
                        .and(liked.contentsId.eq(contentsId))
                        .and(liked.contentsType.eq(contentsType)))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Page<Liked> findByUserIdAndContentsTypeWithPage(Long userId, ContentsTypeEnum contentsTypeEnum, Pageable pageable) {
        QLiked liked = QLiked.liked;

        List<Liked> likedList = jpaQueryFactory.select(liked)
                .from(liked)
                .where(liked.user.id.eq(userId)
                        .and(liked.contentsType.eq(contentsTypeEnum)))
                .orderBy(liked.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(likedList, pageable, likedList.size());

    }

    @Override
    public List<Long> findPostIdsByUserId(Long userId) {
        QLiked liked = QLiked.liked;

        List<Long> result = jpaQueryFactory.select(liked.contentsId)
                .from(liked)
                .where(liked.user.id.eq(userId)
                        .and(liked.contentsType.eq(ContentsTypeEnum.POST)))
                .stream().toList();

        return result;
    }

    @Override
    public List<Long> findCommentIdsByUserId(Long userId) {
        QLiked liked = QLiked.liked;

        List<Long> result = jpaQueryFactory.select(liked.contentsId)
                .from(liked)
                .where(liked.user.id.eq(userId)
                        .and(liked.contentsType.eq(ContentsTypeEnum.COMMENT)))
                .stream().toList();

        return result;
    }
}
