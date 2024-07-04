package com.sparta.plusweekassignment.domain.post.repository;


import com.sparta.plusweekassignment.domain.post.entity.Post;
import com.sparta.plusweekassignment.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface PostRepositoryQuery {

    List<Post> findByUserInOrderByCreatedAtDesc(List<User> followedUsers);

    List<Post> findFollowerPostsByOrderByCreatedAtDesc(Long id, Pageable pageable);

    List<Post> findFollowerPostsOrderByUserId(Long id, Pageable pageable);
}
