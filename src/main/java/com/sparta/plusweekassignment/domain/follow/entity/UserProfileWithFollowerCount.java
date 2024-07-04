package com.sparta.plusweekassignment.domain.follow.entity;

import com.sparta.plusweekassignment.domain.user.entity.User;
import lombok.Getter;

@Getter
public class UserProfileWithFollowerCount {
    private final String username;
    private final long followerCount;

    public UserProfileWithFollowerCount(String username, long followerCount) {
        this.username = username;
        this.followerCount = followerCount;
    }
}