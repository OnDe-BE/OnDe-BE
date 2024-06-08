package com.ott.ond.email.controller;

import com.ott.ond.email.dto.EmailPostRequest;
import com.ott.ond.email.dto.EmailPostResponse;
import com.ott.ond.email.dto.EmailVerficationRequest;
import com.ott.ond.email.dto.EmailVerficationResponse;
import com.ott.ond.email.entity.Email;
import com.ott.ond.email.service.EmailService;
import com.ott.ond.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/send-mail")
@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;
    private String code;

    // 임시 비밀번호 발급

    // 회원가입 이메일 인증 - 요청 시 body로 인증번호 반환하도록 작성하였음
    @PostMapping("/email")
    public Response<EmailPostResponse> sendJoinMail(@RequestBody EmailPostRequest emailPostDto) {
        Email emailMessage = Email.builder()
                .to(emailPostDto.getEmail())
                .subject("[ONED] 이메일 인증을 위한 인증 코드 발송")
                .build();

        code = emailService.sendMail(emailMessage, "email");

        EmailPostResponse emailResponseDto = new EmailPostResponse();
        emailResponseDto.setCode(code);

        return Response.success(emailResponseDto);
    }

    // 회원가입시 일치여부 확인
    @PostMapping("/isTrue")
    public Response<EmailVerficationResponse> isTrue(@RequestBody EmailVerficationRequest emailVerficationRequest) {
        if(code.equals(emailVerficationRequest.getCode())){
            EmailVerficationResponse emailVerficationResponse = new EmailVerficationResponse();
            emailVerficationResponse.setAnswer("인증이 성공하였습니다!");
            return Response.success(new EmailVerficationResponse());
        }else{
            return null;
        }
    }
}