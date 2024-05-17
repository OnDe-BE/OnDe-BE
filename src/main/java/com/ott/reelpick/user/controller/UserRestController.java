package com.ott.reelpick.user.controller;

import com.ott.reelpick.user.dto.UserJoinRequest;
import com.ott.reelpick.user.dto.UserJoinResponse;
import com.ott.reelpick.user.dto.UserLoginRequest;
import com.ott.reelpick.user.dto.UserLoginResponse;
import com.ott.reelpick.user.entity.User;
import com.ott.reelpick.user.service.UserService;
import com.ott.reelpick.util.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users/")
public class UserRestController {
    private final UserService userService;
    private final BCryptPasswordEncoder encoder;
    /* 자체 회원가입 */
    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest userJoinRequest) {
        User join = userService.join(userJoinRequest.toEntity(encoder.encode(userJoinRequest.getPassword())));
        UserJoinResponse userJoinResponse = new UserJoinResponse(join);
        return Response.success(userJoinResponse);
    }

    /* 로그인 */
    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        String token = userService.login(userLoginRequest.getUserId(), userLoginRequest.getPassword());
        HttpSession session = request.getSession();
        session.setAttribute("name", userLoginRequest.getUserId());
        return Response.success(new UserLoginResponse(token));
    }
//    /* 로그아웃 */
//    @PutMapping("/logout")
//    public void logout(){}
//
//    /* 회원정보 조회 */
//    @GetMapping("/{id}")
//    public void get(){}
//
//    /* 회원정보 수정 */
//    @PutMapping("/{id}")
//    public void edit(){}
//
//    /* 회원 탈퇴 */
//    @DeleteMapping("/{id}")
//    public void remove(){}
}
