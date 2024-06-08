package com.ott.ond.user.dto;

import lombok.Data;

@Data
public class CreateAccessTokenRequest {
    private String refreshToken;
}
