package com.sparta.plusweekassignment.domain.post.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.plusweekassignment.domain.post.entity.Post;
import com.sparta.plusweekassignment.domain.post.entity.QPost;
import com.sparta.plusweekassignment.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepositoryQueryImpl implements PostRepositoryQuery {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> findByUserInOrderByCreatedAtDesc(List<User> followedUsers) {
        QPost post = QPost.post;

        List<Post> result = jpaQueryFactory.selectFrom(post)
                .where(post.user.in(followedUsers))
                .orderBy(post.createdAt.desc())
                .fetch();

        return result;
    }

    @Override
    public List<Post> findFollowerPostsByOrderByCreatedAtDesc(Long id, Pageable pageable) {
        QPost post = QPost.post;

        List<Post> result = jpaQueryFactory.selectFrom(post)
                .where(post.user.id.eq(id))
                .orderBy(post.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return result;
    }

    @Override
    public List<Post> findFollowerPostsOrderByUserId(Long id, Pageable pageable) {
        QPost post = QPost.post;

        List<Post> result = jpaQueryFactory.selectFrom(post)
                .where(post.user.id.eq(id))
                .orderBy(post.user.username.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return result;
    }
}
