package com.ott.onde.user.controller;

import com.ott.onde.global.oauth2.util.AuthenticationLogger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MainController {
    private final AuthenticationLogger authenticationLogger;

    @GetMapping(value = {"/","/main"})
    public String Main(Authentication authentication) {
        authenticationLogger.logAuthenticationDetails(authentication);
        return "main";  // 인증되지 않은 경우에도 메인 페이지로
    }
}
