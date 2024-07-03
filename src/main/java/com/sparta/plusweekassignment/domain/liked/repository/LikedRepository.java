package com.sparta.plusweekassignment.domain.liked.repository;

import com.sparta.plusweekassignment.domain.comment.entity.Comment;
import com.sparta.plusweekassignment.domain.liked.entity.Liked;
import com.sparta.plusweekassignment.domain.liked.entity.ContentsTypeEnum;
import com.sparta.plusweekassignment.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikedRepository extends JpaRepository<Liked, Long> {
    int countByUserIdAndContentsType(Long userId, ContentsTypeEnum contentsType);

    Optional<Liked> findByUserIdAndContentsIdAndContentsType(Long id, Long contentsId, ContentsTypeEnum contentsType);

    Page<Liked> findByUserIdAndContentsIdAndContentsTypeOrderByCreatedAtDesc(Long id, String byId, ContentsTypeEnum contentsTypeEnum, Pageable pageable);
}
