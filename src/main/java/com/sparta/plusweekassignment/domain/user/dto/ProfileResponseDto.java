package com.sparta.plusweekassignment.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
public class ProfileResponseDto {


    private String username;
    private String nickname;
    private int likedPostsCount;
    private int likedCommentsCount;
}
