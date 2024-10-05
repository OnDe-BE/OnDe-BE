package com.ott.onde.user.controller;

import com.ott.onde.config.jwt.TokenProvider;
import com.ott.onde.user.entity.User;
import com.ott.onde.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Enumeration;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MainController {
    private final UserService userService;
    private final TokenProvider tokenProvider;

    @GetMapping("/")
    public String Main(Model model, HttpServletRequest request) {
        return "main";
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        // 1. 헤더가 null인지 확인
        if (bearerToken == null) {
            log.info("Authorization 헤더가 존재하지 않습니다.");
            return null;
        }
        log.info("Authorization 헤더 : " + bearerToken);

        // 2. 헤더가 Bearer로 시작하는지 확인
        if (bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);  // "Bearer " 이후의 토큰 부분을 반환
        } else {
            log.info("Authorization 헤더가 Bearer로 시작하지 않습니다. 헤더 값: " + bearerToken);
        }

        return null;
    }
}
