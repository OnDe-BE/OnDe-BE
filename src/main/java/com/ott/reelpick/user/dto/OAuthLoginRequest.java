package com.ott.reelpick.user.dto;

import lombok.Data;

@Data
public class OAuthLoginRequest {
    private String username;
    private String provider;
}
