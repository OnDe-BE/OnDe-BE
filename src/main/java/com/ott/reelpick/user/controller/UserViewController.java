package com.ott.reelpick.user.controller;

import com.ott.reelpick.user.dto.UserJoinDTO;
import com.ott.reelpick.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users/")
public class UserViewController {
    private final UserService userService;
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/join")
    public String join(UserJoinDTO userJoinDTO){
        userService.join(userJoinDTO);
        return "redirect:/users/login";
    }
}
