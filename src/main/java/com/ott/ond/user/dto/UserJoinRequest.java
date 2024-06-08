package com.ott.ond.user.dto;

import com.ott.ond.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserJoinRequest {
    private String userId;
    private String password;
    private int age;
    private String gender;
    private String userName;
    private String nationality;
    private String email;
    private String provider;

    public User toEntity(String password){
        return new User(this.userId, password, this.age, this.gender, this.userName, this.nationality, this.email, this.provider);
    }
}
