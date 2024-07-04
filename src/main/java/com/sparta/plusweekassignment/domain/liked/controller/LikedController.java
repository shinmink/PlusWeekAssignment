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
import org.springframework.data.domain.Sort;
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

//    @GetMapping("/posts")
//    public ResponseEntity<CommonResponseDto<Page<LikedResponseDto>>> getLikedPosts(
//            @AuthenticationPrincipal UserDetailsImpl user,
//            @RequestParam(required = false, defaultValue = "1") int page,
//            @RequestParam(required = false, defaultValue = "5") int size,
//            @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
//            @RequestParam(required = false, defaultValue = "false") boolean isAsc
//    ) {
//        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
//        Sort sort = Sort.by(direction, sortBy);
//        Pageable pageable = PageRequest.of(page - 1, size, sort);
//        Page<LikedResponseDto> likedPosts = likedService.getLikedPosts(user, pageable);
//        return ResponseEntity.ok()
//                .body(CommonResponseDto.<Page<LikedResponseDto>>builder()
//                        .statusCode(HttpStatus.OK.value())
//                        .message("좋아요 한 게시글 목록 조회 성공")
//                        .data(likedPosts)
//                        .build());
//    }

    @GetMapping("/comments")
    public ResponseEntity<CommonResponseDto<Page<LikedResponseDto>>> getLikedComments(
            @AuthenticationPrincipal UserDetailsImpl user,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "5") int size,
            @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(required = false, defaultValue = "false") boolean isAsc
    ) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<LikedResponseDto> likedComments = likedService.getLikedComments(user, pageable);
        return ResponseEntity.ok()
                .body(CommonResponseDto.<Page<LikedResponseDto>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("좋아요 한 댓글 목록 조회 성공")
                        .data(likedComments)
                        .build());
    }
}
