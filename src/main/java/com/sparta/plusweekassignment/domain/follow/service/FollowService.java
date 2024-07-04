package com.sparta.plusweekassignment.domain.follow.service;

import com.sparta.plusweekassignment.domain.follow.dto.FollowRequestDto;
import com.sparta.plusweekassignment.domain.follow.dto.FollowResponseDto;
import com.sparta.plusweekassignment.domain.follow.entity.Follow;
import com.sparta.plusweekassignment.domain.follow.entity.UserProfileWithFollowerCount;
import com.sparta.plusweekassignment.domain.follow.repository.FollowRepository;
import com.sparta.plusweekassignment.domain.post.dto.PostResponseDto;
import com.sparta.plusweekassignment.domain.post.entity.Post;
import com.sparta.plusweekassignment.domain.post.repository.PostRepository;
import com.sparta.plusweekassignment.domain.user.entity.User;
import com.sparta.plusweekassignment.domain.user.repository.UserRepository;
import com.sparta.plusweekassignment.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public FollowResponseDto followUser(FollowRequestDto followRequestDto, UserDetailsImpl user) {
        User currentUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        User targetUser = userRepository.findByUsername(followRequestDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("팔로우할 사용자를 찾을 수 없습니다."));

        if (currentUser.getUsername().equals(followRequestDto.getUsername())) {
            throw new IllegalArgumentException("본인은 팔로우할 수 없습니다.");
        }

        if (!followRepository.findByFromUserAndToUser(currentUser, targetUser).isEmpty()) {
            throw new IllegalArgumentException("이미 팔로우한 사용자입니다.");
        }

        Follow follow = new Follow(currentUser, targetUser);
        followRepository.save(follow);

        return new FollowResponseDto(follow);
    }

    @Transactional
    public FollowResponseDto unfollowUser(FollowRequestDto followRequestDto, UserDetailsImpl user) {
        User currentUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        User targetUser = userRepository.findByUsername(followRequestDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("팔로우할 사용자를 찾을 수 없습니다."));

        Follow follow = followRepository.findByFromUserAndToUser(currentUser, targetUser)
                .orElseThrow(() -> new IllegalArgumentException("팔로우 관계가 존재하지 않습니다."));

        followRepository.delete(follow);

        return new FollowResponseDto(follow);
    }

    @Transactional
    public List<PostResponseDto> getFollowerPosts(UserDetailsImpl user, Pageable pageable) {
        List<Post> followerPostsPage;
        Sort sort = pageable.getSort();

        // Sort 객체에서 정렬 속성을 가져옵니다.
        boolean sortByCreatedAt = sort.get().anyMatch(order -> order.getProperty().equals("createdAt"));
        boolean sortByUser = sort.get().anyMatch(order -> order.getProperty().equals("User"));

        if (sortByCreatedAt) {
            followerPostsPage = postRepository.findFollowerPostsByOrderByCreatedAtDesc(user.getUser().getId(), pageable);
        } else if (sortByUser) {
            followerPostsPage = postRepository.findFollowerPostsOrderByUserId(user.getUser().getId(), pageable);
        } else {
            followerPostsPage = postRepository.findFollowerPostsByOrderByCreatedAtDesc(user.getUser().getId(), pageable);
        }



        return followerPostsPage.stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }


    public List<UserProfileWithFollowerCount> getTop10Followers() {
        List<UserProfileWithFollowerCount> top10Followers = userRepository.findTop10ByOrderByFollowersDesc()
                .stream()
                .map(user -> new UserProfileWithFollowerCount(user.getUsername(), followRepository.countByToUser(user)))
                .collect(Collectors.toList());

        return top10Followers;
    }


}
