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
    @NotNull
    private int age;
    @NotNull
    private String gender;
    @NotNull
    private String nickname;
    @NotNull
    private String nationality;
    @NotNull
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
