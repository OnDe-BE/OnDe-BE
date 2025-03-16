package com.ott.onde.user.dto;

import com.ott.onde.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserJoinRequest {
    private String userId;
    private String password;
    private int age;
    private String gender;
    private String nickname;
    private String nationality;
    private String email;
    private String provider;
    private List<String> preferGenreList;
    private List<String> preferSentenceList;


    public User toEntity(String password){
        return new User(this.userId, password, this.age, this.gender, this.nickname, this.nationality, this.email, this.provider);
    }
}
