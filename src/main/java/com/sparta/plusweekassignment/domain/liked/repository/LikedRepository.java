package com.sparta.plusweekassignment.domain.liked.repository;

import com.sparta.plusweekassignment.domain.liked.entity.ContentsTypeEnum;
import com.sparta.plusweekassignment.domain.liked.entity.Liked;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikedRepository extends JpaRepository<Liked, Long> {
    Optional<Liked> findByUserIdAndContentsIdAndContentsType(Long user_id, Long contentsId, ContentsTypeEnum contentsType);
}
