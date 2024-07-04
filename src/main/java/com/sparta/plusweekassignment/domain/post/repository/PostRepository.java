package com.sparta.plusweekassignment.domain.post.repository;

import com.sparta.plusweekassignment.domain.liked.entity.Liked;
import com.sparta.plusweekassignment.domain.post.dto.PostPageResponseDto;
import com.sparta.plusweekassignment.domain.post.entity.Post;
import com.sparta.plusweekassignment.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.List;

@RepositoryDefinition(domainClass = Post.class, idClass = Long.class)
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryQuery {

}
