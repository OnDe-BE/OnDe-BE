package com.ott.reelpick.user.dto;

import lombok.Data;

@Data
public class UserJoinDTO {
    private String id;
    private String password;
    private int age;
    private String gender;
    private String nickname;
    private String nationality;
    private String email;
}
