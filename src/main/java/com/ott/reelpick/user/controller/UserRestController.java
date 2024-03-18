package com.ott.reelpick.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor

@RequestMapping("/users/*")
public class UserRestController {
    /* 자체 회원가입 */
    @PostMapping("/join")

    /* 로그인 */
    @PostMapping("/login")

    /* 로그아웃 */
    @PutMapping("/logout")

    /* 회원정보 조회 */
    @GetMapping("/{id}")

    /* 회원정보 수정 */
    @PutMapping("/{id}")

    /* 회원 탈퇴 */
    @DeleteMapping("/{id}")
}
