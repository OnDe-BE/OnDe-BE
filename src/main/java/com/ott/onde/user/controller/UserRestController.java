package com.ott.onde.user.controller;

import com.ott.onde.config.jwt.GlobalResDTO;
import com.ott.onde.user.dto.*;
import com.ott.onde.user.entity.User;
import com.ott.onde.user.service.UserService;
import com.ott.onde.util.Response;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
        User join = userService.join(userJoinRequest);

        UserJoinResponse userJoinResponse = new UserJoinResponse(join);
        return Response.success(userJoinResponse);
    }

    /* 로그인 */
   @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest, HttpServletResponse response) {
        // UserService에서 login 메소드 호출
        GlobalResDTO globalResDTO = userService.login(userLoginRequest.getUserId(), userLoginRequest.getPassword(), response);

        // GlobalResDTO에서 data 부분만 꺼내서 UserLoginResponse로 래핑
        UserLoginResponse userLoginResponse = (UserLoginResponse) globalResDTO.getData();

        log.info("token :{}, {}", userLoginResponse.getAccessToken(),userLoginResponse.getRefreshToken());

        // Response.success로 성공 응답 반환
        return Response.success(userLoginResponse);
    }

    /* 프로필 값 가져오기 */
//    @GetMapping("/profile")
//    public Response<UserInfoResponse> findUsersProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        UserInfoResponse userInfoResponse = userService.getInfo(userDetails.user());
//        log.info(userInfoResponse.getUserId());
//        return Response.success(userInfoResponse);
//    }
    /* 아이디 찾기 */
    @PostMapping("/findId")
    public Response<UserFindIdResponse> findId(@RequestBody UserFindIdRequest userFindIdRequest){
        String id = userService.findId(userFindIdRequest.getEmail());
        UserFindIdResponse userFindIdResponse = new UserFindIdResponse();
        userFindIdResponse.setId(id);
        return Response.success(userFindIdResponse);
    }

    //
    /* 회원정보 조회 */
    @GetMapping("/profile")
    public Response<UserInfoResponse> getUserInfo(@RequestBody UserInfoRequest userInfoRequest){
        String userId = userInfoRequest.getId();
        UserInfoResponse userInfoResponse = userService.getInfo(userId);
        return Response.success(userInfoResponse);
    }

    /* 회원정보 수정 */
    @PutMapping("/{id}")
    public void edit(){}


    /* 회원 탈퇴 */
    @DeleteMapping("/{id}")
    public void remove(@RequestBody UserDeleteRequest userDeleteRequest){
        String userId = userDeleteRequest.getId();
        Long id = null;
    }
}
