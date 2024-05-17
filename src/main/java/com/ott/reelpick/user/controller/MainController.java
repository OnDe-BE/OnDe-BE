package com.ott.reelpick.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MainController {
    @GetMapping("/")
    public String Main(Model model) {
        return "main";
    }
}
