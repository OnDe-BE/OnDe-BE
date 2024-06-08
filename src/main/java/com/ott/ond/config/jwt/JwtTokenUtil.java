package com.ott.ond.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

@Component
public class JwtTokenUtil {
    public static String createToken(String userId, String key, Duration expireTime) {
        Claims claims = Jwts.claims();
        claims.put("userId", userId);

        long expireTimeMs = expireTime.toMillis(); // Duration을 밀리초로 변환

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))//현재시간
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))//현재시간 + 종료시간 = 만료 시간
                .signWith(SignatureAlgorithm.HS256, key) //해싱 알고리즘으로 입력받은 키를 암호화
                .compact();
    }
}
