package com.ott.reelpick.user.dto;

import com.ott.reelpick.user.entity.User;
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
