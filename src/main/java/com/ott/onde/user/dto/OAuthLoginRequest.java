package com.ott.onde.user.dto;

import lombok.Data;

@Data
public class OAuthLoginRequest {
    private String userCode;
    private String username;
    private String id;
}
