package com.ott.onde.user.dto;

import com.ott.onde.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDeleteResponse {
    private String userId;

    public UserDeleteResponse(User user) {
        this.userId = user.getId();
    }
}
