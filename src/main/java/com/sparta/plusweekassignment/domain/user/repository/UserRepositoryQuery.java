package com.sparta.plusweekassignment.domain.user.repository;


import com.sparta.plusweekassignment.domain.post.entity.Post;
import com.sparta.plusweekassignment.domain.user.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


public interface UserRepositoryQuery {

    Optional<User> findByUsername(String username);

    Collection<User> findTop10ByOrderByFollowersDesc();
}
