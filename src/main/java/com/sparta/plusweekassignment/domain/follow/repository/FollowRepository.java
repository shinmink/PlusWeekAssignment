package com.sparta.plusweekassignment.domain.follow.repository;

import com.sparta.plusweekassignment.domain.follow.entity.Follow;
import com.sparta.plusweekassignment.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFromUserAndToUser(User fromUser, User toUser);

    List<Follow> findByFromUser(User currentUser);

    Long countByToUser(User user);
}
