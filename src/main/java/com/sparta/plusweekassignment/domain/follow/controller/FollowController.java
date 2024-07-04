package com.sparta.plusweekassignment.domain.follow.controller;

import com.sparta.plusweekassignment.domain.common.CommonResponseDto;
import com.sparta.plusweekassignment.domain.follow.dto.FollowRequestDto;
import com.sparta.plusweekassignment.domain.follow.dto.FollowResponseDto;
import com.sparta.plusweekassignment.domain.follow.service.FollowService;
import com.sparta.plusweekassignment.domain.liked.dto.LikedResponseDto;
import com.sparta.plusweekassignment.domain.post.dto.PostResponseDto;
import com.sparta.plusweekassignment.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/user/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping
    public ResponseEntity<CommonResponseDto<FollowResponseDto>> followUser(
            @RequestBody FollowRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl user
    ){
        FollowResponseDto responseDto = followService.followUser(requestDto, user);
        return ResponseEntity.ok()
                .body(CommonResponseDto.<FollowResponseDto>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("팔로우 성공")
                        .data(responseDto)
                        .build());
    }

    @DeleteMapping
    public ResponseEntity<CommonResponseDto<FollowResponseDto>> unfollowUser(
            @RequestBody FollowRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl user
    ){
        FollowResponseDto responseDto = followService.unfollowUser(requestDto, user);
        return ResponseEntity.ok()
                .body(CommonResponseDto.<FollowResponseDto>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("언팔로우 성공")
                        .data(responseDto)
                        .build());
    }

//    @GetMapping
//    public ResponseEntity<CommonResponseDto<Page<PostResponseDto>>> getFollowedUserPosts(
//            @AuthenticationPrincipal UserDetailsImpl user,
//            @RequestParam(required = false, defaultValue = "1") int page,
//            @RequestParam(required = false, defaultValue = "5") int size,
//            @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
//            @RequestParam(required = false, defaultValue = "false") boolean isAsc
//    ){
//        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
//        Sort sort = Sort.by(direction, sortBy);
//        Pageable pageable = PageRequest.of(page - 1, size, sort);
//        Page<PostResponseDto> responseDtos = followService.getFollowedUserPosts(user, pageable);
//        return ResponseEntity.ok()
//                .body(CommonResponseDto.<Page<PostResponseDto>>builder()
//                        .statusCode(HttpStatus.OK.value())
//                        .message("팔로우한 사용자의 게시물 조회 성공")
//                        .data(responseDtos)
//                        .build());
//    }

    @GetMapping
    public ResponseEntity<CommonResponseDto<List<PostResponseDto>>> getFollowerPosts(
            @AuthenticationPrincipal UserDetailsImpl user,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "5") int size,
            @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(required = false, defaultValue = "false") boolean isAsc
    ){
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        List<PostResponseDto> responseDtos = followService.getFollowerPosts(user, pageable);
        return ResponseEntity.ok()
                .body(CommonResponseDto.<List<PostResponseDto>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("팔로우한 사용자의 게시물 조회 성공")
                        .data(responseDtos)
                        .build());
    }

}
