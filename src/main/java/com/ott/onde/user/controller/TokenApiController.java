package com.ott.onde.user.controller;

import com.ott.onde.config.jwt.TokenProvider;
import com.ott.onde.user.entity.User;
import com.ott.onde.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Map;

@RestController
@RequestMapping("/api/token")
@RequiredArgsConstructor
public class TokenApiController {
    private final TokenProvider tokenProvider;
    private final UserService userService;

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(@CookieValue(value = "refreshToken", required = false) String refreshToken) {
        System.out.println("Refresh token: " + refreshToken);
        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token is missing.");
        }

        // refresh_token 검증
        boolean isValid = tokenProvider.tokenValidation(refreshToken);
        if (!isValid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token.");
        }

        // refresh_token에서 사용자 정보 추출
        String userId = tokenProvider.getUserIdFromToken(refreshToken);
        User user = userService.findById(userId);

        // 새로운 access_token 생성
        String newAccessToken = tokenProvider.createToken(user.getUserCode(), String.valueOf(Duration.ofHours(1)));

        // 새로운 access_token을 반환
        return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
    }
}
