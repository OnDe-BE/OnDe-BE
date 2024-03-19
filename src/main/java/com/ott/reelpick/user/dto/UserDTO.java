package com.ott.reelpick.user.dto;

import lombok.Builder;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

@Data
public class UserDTO {
    @NotNull
    private String id;
    @NotNull
    private String password;
    private int age;
    private String gender;
    private String nickname;
    private String nationality;
    private String email;

    @Builder
    public UserDTO(String id, String password, int age, String gender, String nickname, String nationality, String email){
        this.id = id;
        this.password = password;
        this.age = age;
        this.gender = gender;
        this.nickname = nickname;
        this.nationality = nationality;
        this.email = email;
    }
}
