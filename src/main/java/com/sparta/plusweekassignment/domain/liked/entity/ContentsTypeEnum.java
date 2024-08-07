package com.sparta.plusweekassignment.domain.liked.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ContentsTypeEnum {
    COMMENT("comment"),
    POST("post");
    private final String contentsType;
}
