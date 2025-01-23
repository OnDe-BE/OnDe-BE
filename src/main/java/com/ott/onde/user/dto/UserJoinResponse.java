package com.ott.onde.user.dto;

import com.ott.onde.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserJoinResponse {
    private String userId;
    private String userName;

    public UserJoinResponse(User user) {
        this.userId = user.getId();
        this.userName = user.getNickname();
    }
}
