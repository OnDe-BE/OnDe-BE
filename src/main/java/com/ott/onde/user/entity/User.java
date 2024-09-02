package com.ott.onde.user.entity;

import com.ott.onde.post.entity.Post;
import com.ott.onde.user.dto.UserJoinRequest;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @Column(name = "user_idx", updatable = false)
    private Long userId;
    private String id;

    @Column(name = "password")
    private String password;

    private int age;
    private String gender;
    private String nickname;
    private String nationality;
    private String email;

    @Column(name = "provider")
    private String provider;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();


    public User(String id, String password, int age, String gender, String nickname, String nationality, String email, String provider) {
        this.id = id;
        this.password = password;
        this.age = age;
        this.gender = gender;
        this.nickname = nickname;
        this.nationality = nationality;
        this.email = email;
        this.provider = provider;
    }

    public User(UserJoinRequest userJoinRequest) {
        this.id = userJoinRequest.getNickname();
        this.password = userJoinRequest.getPassword();
        this.age = userJoinRequest.getAge();
        this.gender = userJoinRequest.getGender();
        this.nickname = userJoinRequest.getNickname();
        this.nationality = userJoinRequest.getNationality();
        this.email = userJoinRequest.getEmail();
        this.provider = userJoinRequest.getProvider();
    }

    public User update(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
        return this;
    }
}
