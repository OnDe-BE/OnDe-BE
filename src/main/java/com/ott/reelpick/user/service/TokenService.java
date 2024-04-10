package com.ott.reelpick.user.service;

import com.ott.reelpick.config.jwt.TokenProvider;
import com.ott.reelpick.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    public String createNewAccessToken(String refreshToken) {
        // 토큰 유효성 검사에 실패하면 예외 발생
        if(!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }

        Long userIdx = refreshTokenService.findByRefreshToken(refreshToken).getUserIdx();
        User user = userService.findById(userIdx);

        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }
}
