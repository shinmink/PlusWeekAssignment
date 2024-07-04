package com.sparta.plusweekassignment.domain.liked.repository;

import com.sparta.plusweekassignment.domain.liked.entity.ContentsTypeEnum;
import com.sparta.plusweekassignment.domain.liked.entity.Liked;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface LikedRepositoryQuery {
    int countByUserIdAndContentsType(Long userId, ContentsTypeEnum contentsType);

    Optional<Liked> findByUserIdAndContentsIdAndContentsType(Long id, Long contentsId, ContentsTypeEnum contentsType);

    Page<Liked> findByUserIdAndContentsTypeWithPage(Long userId, ContentsTypeEnum contentsTypeEnum, Pageable pageable);

    List<Long> findPostIdsByUserId(Long userId);

    List<Long> findCommentIdsByUserId(Long userId);
}
