package com.sparta.plusweekassignment.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.plusweekassignment.domain.post.entity.Post;
import com.sparta.plusweekassignment.domain.post.entity.QPost;
import com.sparta.plusweekassignment.domain.user.entity.QUser;
import com.sparta.plusweekassignment.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

@Repository
@RequiredArgsConstructor
public class UserRepositoryQueryImpl implements UserRepositoryQuery {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<User> findByUsername(String username) {
        QUser user = QUser.user;

        User resultUser = jpaQueryFactory.selectFrom(user)
                .where(user.username.eq(username))
                .fetchOne();

        return Optional.ofNullable(resultUser);
    }

    @Override
    public Collection<User> findTop10ByOrderByFollowersDesc() {
        QUser user = QUser.user;

        List<User> result = jpaQueryFactory
                .selectFrom(user)
                .where(user.followers.isNotEmpty())
                .orderBy(user.followers.size().desc())
                .fetch();

        return result;
    }
}
