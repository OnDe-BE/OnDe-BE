package com.ott.reelpick.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MainController {
    @GetMapping("/")
    public String Main(Model model, Authentication authentication) {
        OAuth2User oAuth2User;

        try {
            oAuth2User = (OAuth2User) authentication.getPrincipal();
        } catch (NullPointerException e) {
            log.info("로그인 정보 확인 안됨!");
            return "main";
        }
        model.addAttribute("user", oAuth2User.getAttribute("email"));
        return "main";
    }
}
