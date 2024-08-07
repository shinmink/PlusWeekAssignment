package com.sparta.plusweekassignment.domain.follow.entity;

import com.sparta.plusweekassignment.domain.common.TimeStampEntity;
import com.sparta.plusweekassignment.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Follow extends TimeStampEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_user")
    private User fromUser;  // 팔로우한 사용자

    @ManyToOne
    @JoinColumn(name = "to_user")
    private User toUser; // 팔로잉된 사용자

    public Follow(User fromUser, User toUser){
        this.fromUser = fromUser;
        this.toUser = toUser;
    }
}
