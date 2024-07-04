package com.sparta.plusweekassignment.domain.follow.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.plusweekassignment.domain.follow.entity.Follow;
import com.sparta.plusweekassignment.domain.follow.entity.QFollow;
import com.sparta.plusweekassignment.domain.user.entity.User;
import com.sparta.plusweekassignment.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.sparta.plusweekassignment.domain.liked.entity.QLiked.liked;

@Repository
@RequiredArgsConstructor
public class FollowRepositoryQueryImpl implements FollowRepositoryQuery{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Follow> findByFromUserAndToUser(User fromUser, User toUser) {
        QFollow follow = QFollow.follow;

        Follow result = jpaQueryFactory.selectFrom(follow)
                .where(follow.fromUser.eq(fromUser)
                        .and(follow.toUser.eq(toUser)))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public List<Follow> findByFromUser(User currentUser) {
        QFollow follow = QFollow.follow;

        List<Follow> result = jpaQueryFactory.selectFrom(follow)
                .where(follow.fromUser.eq(currentUser))
                .fetch();

        return result;
    }

    @Override
    public Long countByToUser(User user) {
        QFollow follow = QFollow.follow;

        Long result = jpaQueryFactory.select(follow.count())
                .from(follow)
                .where(follow.toUser.eq(user))
                .fetchOne();

        return result;
    }
}
