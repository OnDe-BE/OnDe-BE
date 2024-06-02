package com.ott.reelpick.user.dto;

import lombok.Data;

@Data
public class UserInfoResponse {
    private String id;
    private String password;
    private int age;
    private String gender;
    private String userName;
    private String nationality;
    private String email;
}
