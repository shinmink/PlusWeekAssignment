package com.sparta.plusweekassignment.domain.follow.repository;

import com.sparta.plusweekassignment.domain.follow.entity.Follow;
import com.sparta.plusweekassignment.domain.liked.entity.Liked;
import com.sparta.plusweekassignment.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;
import java.util.Optional;

@RepositoryDefinition(domainClass = Follow.class, idClass = Long.class)
public interface FollowRepository extends JpaRepository<Follow, Long>, FollowRepositoryQuery{

}
