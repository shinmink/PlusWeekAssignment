package com.sparta.plusweekassignment.domain.liked.controller;

import com.sparta.plusweekassignment.domain.common.CommonResponseDto;
import com.sparta.plusweekassignment.domain.liked.dto.LikedRequestDto;
import com.sparta.plusweekassignment.domain.liked.dto.LikedResponseDto;
import com.sparta.plusweekassignment.domain.liked.service.LikedService;
import com.sparta.plusweekassignment.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/like")
public class LikedController {

    private final LikedService likedService;

    @PostMapping
    public ResponseEntity<CommonResponseDto<LikedResponseDto>> addLike(
            @AuthenticationPrincipal UserDetailsImpl user,
            @RequestBody LikedRequestDto requestDto
    ) {
        LikedResponseDto responseDto = likedService.addLike(requestDto, user);
        return ResponseEntity.ok()
                .body(CommonResponseDto.<LikedResponseDto>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("좋아요 추가 성공")
                        .data(responseDto)
                        .build());
    }

    @DeleteMapping
    public ResponseEntity<CommonResponseDto<LikedResponseDto>> removeLike(
            @AuthenticationPrincipal UserDetailsImpl user,
            @RequestBody LikedRequestDto requestDto
    ) {
        likedService.removeLike(requestDto, user);
        return ResponseEntity.ok()
                .body(CommonResponseDto.<LikedResponseDto>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("좋아요 제거 성공")
                        .build());
    }

    @GetMapping
    public ResponseEntity<CommonResponseDto<Page<LikedResponseDto>>> getLikedPosts(
            @AuthenticationPrincipal UserDetailsImpl user,
            @RequestParam(defaultValue = "0") int page
    ) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<LikedResponseDto> likedPosts = likedService.getLikedPosts(user, pageable);
        return ResponseEntity.ok()
                .body(CommonResponseDto.<Page<LikedResponseDto>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("좋아요 한 게시글 목록 조회 성공")
                        .data(likedPosts)
                        .build());
    }
}
