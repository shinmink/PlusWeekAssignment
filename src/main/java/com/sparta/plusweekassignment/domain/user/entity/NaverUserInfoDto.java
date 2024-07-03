package com.sparta.plusweekassignment.domain.user.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NaverUserInfoDto {

    private Long id;
    private String nickname;

    public NaverUserInfoDto(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }
}