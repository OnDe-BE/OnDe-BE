package com.ott.reelpick.user.controller;

import com.ott.reelpick.user.dto.*;
import com.ott.reelpick.user.entity.User;
import com.ott.reelpick.user.service.UserService;
import com.ott.reelpick.util.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users/")
public class UserRestController {
    private final UserService userService;
    private final BCryptPasswordEncoder encoder;

    /**
     * 회원가입
     * @param userJoinRequest
     * @return
     */
    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest userJoinRequest) {
        User join = userService.join(userJoinRequest.toEntity(encoder.encode(userJoinRequest.getPassword())));
        UserJoinResponse userJoinResponse = new UserJoinResponse(join);
        return Response.success(userJoinResponse);
    }

    /**
     * 로그인
     * @param userLoginRequest
     * @param request
     * @return
     */
    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        String token = userService.login(userLoginRequest.getUserId(), userLoginRequest.getPassword());
        HttpSession session = request.getSession();
        session.setAttribute("name", userLoginRequest.getUserId());
        session.setAttribute("provider", "null");
        return Response.success(new UserLoginResponse(token));
    }
//

    /**
     * 아이디 찾기
     * @param userFindIdRequest
     * @return
     */
    @PostMapping("/findId")
    public Response<UserFindIdResponse> get(@RequestBody UserFindIdRequest userFindIdRequest){
        List<String> userIdList = userService.getUserId(userFindIdRequest.getEmail());
        UserFindIdResponse userFindIdResponseResponse = new UserFindIdResponse(userIdList);
        return Response.success(userFindIdResponseResponse);
    }

    /* 비밀번호 찾기 */


    /* 회원정보 조회 */
    @GetMapping("/{id}")
    public Response<UserInfoRequest> get(@RequestBody UserInfoRequest userInfoRequest){
//        User user = userService.getUserInfo(userInfoRequest.getId(), );
//        UserInfoResponse userInfoResponse = new UserInfoResponse(user);
        return null;
    }

    /* 회원정보 수정 */
    @PutMapping("/{id}")
    public void edit(){}

    /* 회원 탈퇴 */
    @DeleteMapping("/{id}")
    public void remove(){}
}
