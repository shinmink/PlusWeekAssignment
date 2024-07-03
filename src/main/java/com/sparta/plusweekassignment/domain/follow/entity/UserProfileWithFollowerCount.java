package com.sparta.plusweekassignment.domain.follow.entity;

import com.sparta.plusweekassignment.domain.user.entity.User;
import lombok.Getter;

@Getter
public class UserProfileWithFollowerCount {
    private final User user;
    private final long followerCount;

    public UserProfileWithFollowerCount(User user, long followerCount) {
        this.user = user;
        this.followerCount = followerCount;
    }
}