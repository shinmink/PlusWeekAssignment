package com.sparta.plusweekassignment.domain.liked.service;

import com.sparta.plusweekassignment.domain.comment.entity.Comment;
import com.sparta.plusweekassignment.domain.comment.repository.CommentRepository;
import com.sparta.plusweekassignment.domain.liked.dto.LikedRequestDto;
import com.sparta.plusweekassignment.domain.liked.dto.LikedResponseDto;
import com.sparta.plusweekassignment.domain.liked.entity.ContentsTypeEnum;
import com.sparta.plusweekassignment.domain.liked.entity.Liked;
import com.sparta.plusweekassignment.domain.liked.repository.LikedRepository;
import com.sparta.plusweekassignment.domain.post.entity.Post;
import com.sparta.plusweekassignment.domain.post.repository.PostRepository;
import com.sparta.plusweekassignment.domain.user.entity.User;
import com.sparta.plusweekassignment.domain.user.repository.UserRepository;
import com.sparta.plusweekassignment.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikedService {

    private final LikedRepository likedRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public LikedResponseDto addLike(LikedRequestDto likedRequestDto, UserDetailsImpl user) {
        User currentUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Optional<Liked> existingLike = likedRepository.findByUserIdAndContentsIdAndContentsType(currentUser.getId(), likedRequestDto.getContentsId(), likedRequestDto.getContentsType());

        if (existingLike.isPresent()) {
            throw new IllegalArgumentException("이미 좋아요를 눌렀습니다.");
        }

        // 본인이 작성한 게시물이나 댓글에 좋아요를 남길 수 없습니다.
        // POST
        if (likedRequestDto.getContentsType() == ContentsTypeEnum.POST) {
            Post post = postRepository.findById(likedRequestDto.getContentsId())
                    .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));
            if (post.getUser().getId().equals(currentUser.getId())) {
                throw new IllegalArgumentException("본인이 작성한 게시물에는 좋아요를 남길 수 없습니다.");
            }
            post.setLikeCount(post.getLikeCount() + 1); //변경 감지 -> 따로 save 필요없다.
        }
        //COMMENT
        else if (likedRequestDto.getContentsType() == ContentsTypeEnum.COMMENT) {
            Comment comment = commentRepository.findById(likedRequestDto.getContentsId())
                    .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
            if (comment.getUser().getId().equals(currentUser.getId())) {
                throw new IllegalArgumentException("본인이 작성한 댓글에는 좋아요를 남길 수 없습니다.");
            }
            comment.setLikeCount(comment.getLikeCount() + 1);
        }

        Liked liked = new Liked(currentUser, likedRequestDto.getContentsId(), likedRequestDto.getContentsType());
        likedRepository.save(liked);

        return new LikedResponseDto(liked);
    }

    @Transactional
    public void removeLike(LikedRequestDto likedRequestDto, UserDetailsImpl user) {
        User currentUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Liked existingLike = likedRepository.findByUserIdAndContentsIdAndContentsType(currentUser.getId(), likedRequestDto.getContentsId(), likedRequestDto.getContentsType())
                .orElseThrow(() -> new IllegalArgumentException("좋아요가 존재하지 않습니다."));

        // 좋아요 수 감소
        // POST
        if (likedRequestDto.getContentsType() == ContentsTypeEnum.POST) {
            Post post = postRepository.findById(likedRequestDto.getContentsId())
                    .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));
            post.setLikeCount(post.getLikeCount() - 1);
        }
        // COMMENT
        else if (likedRequestDto.getContentsType() == ContentsTypeEnum.COMMENT) {
            Comment comment = commentRepository.findById(likedRequestDto.getContentsId())
                    .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
            comment.setLikeCount(comment.getLikeCount() - 1);
        }

        likedRepository.delete(existingLike);
    }

    public Page<LikedResponseDto> getLikedPosts(UserDetailsImpl user, Pageable pageable) {
        Page<Liked> result = likedRepository.findByUserIdAndContentsTypeWithPage(user.getUser().getId(), ContentsTypeEnum.POST, pageable);

        Page<LikedResponseDto> map = result.map(LikedResponseDto::new);

        return map;
    }

    public Page<LikedResponseDto> getLikedComments(UserDetailsImpl user, Pageable pageable) {
        Page<Liked> result = likedRepository.findByUserIdAndContentsTypeWithPage(user.getUser().getId(), ContentsTypeEnum.COMMENT, pageable);

        Page<LikedResponseDto> map = result.map(LikedResponseDto::new);

        return map;
    }
}
