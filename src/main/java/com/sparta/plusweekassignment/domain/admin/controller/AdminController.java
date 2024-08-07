package com.sparta.plusweekassignment.domain.admin.controller;

import com.sparta.plusweekassignment.domain.admin.dto.UserRoleRequestDto;
import com.sparta.plusweekassignment.domain.common.CommonResponseDto;
import com.sparta.plusweekassignment.domain.post.dto.PostRequestDto;
import com.sparta.plusweekassignment.domain.post.dto.PostResponseDto;
import com.sparta.plusweekassignment.domain.post.service.PostService;
import com.sparta.plusweekassignment.domain.user.entity.User;
import com.sparta.plusweekassignment.domain.user.service.UserService;
import com.sparta.plusweekassignment.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j(topic = "AdminController")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final PostService postService;

    //유저 권한 수정
    // "adminKey" : "7Ja065Oc66+87YKk"
    @PutMapping("/admin/user/{userId}/role")
    public ResponseEntity<String> adminSetUserRole(
            @PathVariable(value = "userId") Long userId,
            @Valid @RequestBody UserRoleRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        userService.adminSetUserRole(userId, requestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(userId + " 번 아이디가 [" + requestDto.getRole() + "] 권한으로 변경 되었습니다.");
    }

    //유저 전체 조회
    @GetMapping("/admin/user")
    public ResponseEntity<CommonResponseDto<Object>> adminGetUserAll(){
        List<User> userList = userService.adminGetUserAll();

        return ResponseEntity.ok()
                .body(CommonResponseDto.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("유저 전체 조회 성공")
                        .data(userList)
                        .build());
    }

    //게시글 수정
    @PutMapping("/admin/post/{postId}")
    public ResponseEntity<CommonResponseDto<PostResponseDto>> adminUpdatePost(
            @Valid @RequestBody PostRequestDto postRequestDto,
            @PathVariable(value = "postId") Long postId
    ) {
        PostResponseDto postResponseDto = postService.adminUpdatePost(postRequestDto, postId);
        return ResponseEntity.ok()
                .body(CommonResponseDto.<PostResponseDto>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("게시물 수정 성공")
                        .data(postResponseDto)
                        .build());
    }

    //게시물 삭제
    @DeleteMapping("/admin/post/{postId}")
    public ResponseEntity<CommonResponseDto<PostResponseDto>> adminDeletePost(
            @PathVariable(value = "postId") Long postId
    ){
        postService.adminDeletePost(postId);
        return ResponseEntity.ok()
                .body(CommonResponseDto.<PostResponseDto>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("게시물 삭제 성공")
                        .build());
    }
}
