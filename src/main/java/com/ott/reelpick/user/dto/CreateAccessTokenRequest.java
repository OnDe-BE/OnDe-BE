package com.ott.reelpick.user.dto;

import lombok.Data;

@Data
public class CreateAccessTokenRequest {
    private String refreshToken;
}
