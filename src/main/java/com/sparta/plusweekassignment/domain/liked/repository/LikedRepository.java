package com.sparta.plusweekassignment.domain.liked.repository;

import com.sparta.plusweekassignment.domain.comment.entity.Comment;
import com.sparta.plusweekassignment.domain.liked.entity.Liked;
import com.sparta.plusweekassignment.domain.liked.entity.ContentsTypeEnum;
import com.sparta.plusweekassignment.domain.post.entity.Post;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;
import java.util.Optional;

@RepositoryDefinition(domainClass = Liked.class, idClass = Long.class)
public interface LikedRepository extends JpaRepository<Liked, Long>, LikedRepositoryQuery {

}
