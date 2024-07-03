package com.sparta.plusweekassignment.domain.liked.dto;

import com.sparta.plusweekassignment.domain.liked.entity.ContentsTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikedRequestDto {
    // contents id ex) 1, 2
    private Long contentsId;
    // contents Type ex) COMMENT , POST
    private ContentsTypeEnum contentsType;
}