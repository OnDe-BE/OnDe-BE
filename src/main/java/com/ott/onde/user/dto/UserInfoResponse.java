package com.ott.onde.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {
    private String userId;
    private String password;
    private int age;
    private String gender;
    private String nickname;
    private String nationality;
    private String email;
}
