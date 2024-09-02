package com.ott.onde.user.dto;

import lombok.Data;

@Data
public class OAuthLoginRequest {
    Long index;
    private String username;
    private String id;
}
