package com.ott.reelpick.user;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    private String id;

    @Column(name = "password")
    private String password;

    private int age;
    private String gender;
    private String nickname;
    private String nationality;
    private String email;
}
