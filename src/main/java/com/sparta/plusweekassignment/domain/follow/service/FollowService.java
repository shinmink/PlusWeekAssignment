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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


public interface FollowService {
//    private final FollowRepository followRepository;
//    private final UserRepository userRepository;
//    private final PostRepository postRepository;
//
//    @Transactional
//    public FollowResponseDto followUser(FollowRequestDto followRequestDto, UserDetailsImpl user) {
//        User currentUser = userRepository.findByUsername(user.getUsername())
//                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
//
//        User targetUser = userRepository.findByUsername(followRequestDto.getUsername())
//                .orElseThrow(() -> new IllegalArgumentException("팔로우할 사용자를 찾을 수 없습니다."));
//
//        if (currentUser.getUsername().equals(followRequestDto.getUsername())) {
//            throw new IllegalArgumentException("본인은 팔로우할 수 없습니다.");
//        }
//
//        if(followRepository.findByFromUserAndToUser(currentUser, targetUser).isPresent()) {
//            throw new IllegalArgumentException("이미 팔로우한 사용자입니다.");
//        }
//
//
//        Follow follow = new Follow(currentUser, targetUser);
//        followRepository.save(follow);
//
//        return new FollowResponseDto(follow);
//
//    }
//
//    @Transactional
//    public FollowResponseDto unfollowUser(FollowRequestDto followRequestDto, UserDetailsImpl user){
//        User currentUser = userRepository.findByUsername(user.getUsername())
//                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
//
//        User targetUser = userRepository.findByUsername(followRequestDto.getUsername())
//                .orElseThrow(() -> new IllegalArgumentException("팔로우할 사용자를 찾을 수 없습니다."));
//
//        Follow follow = followRepository.findByFromUserAndToUser(currentUser, targetUser)
//                .orElseThrow(() -> new IllegalArgumentException("팔로우 관계가 존재하지 않습니다."));
//
//        followRepository.delete(follow);
//
//        return new FollowResponseDto(follow);
//    }
//
//    @Transactional
//    public List<PostResponseDto> getFollowedUserPosts(UserDetailsImpl user) {
//        User currentUser = userRepository.findByUsername(user.getUsername())
//                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
//
//        List<User> followedUsers = followRepository.findByFromUser(currentUser)
//                .stream()
//                .map(Follow::getToUser)
//                .collect(Collectors.toList());
//
//        List<Post> posts = postRepository.findByUserInOrderByCreatedAtDesc(followedUsers);
//
//        return posts.stream()
//                .map(PostResponseDto::new)
//                .collect(Collectors.toList());
//
//    }


    FollowResponseDto followUser(FollowRequestDto followRequestDto, UserDetailsImpl user);

    FollowResponseDto unfollowUser(FollowRequestDto followRequestDto, UserDetailsImpl user);

    List<PostResponseDto> getFollowedUserPosts(UserDetailsImpl user);

    List<PostResponseDto> getFollowerPosts(UserDetailsImpl user, int page, int size, String sortBy);

    List<UserProfileWithFollowerCount> getTop10Followers();

}
