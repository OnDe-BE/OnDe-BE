package com.ott.onde.user.entity;

import com.ott.onde.genre.entity.PreferGenre;
import com.ott.onde.post.entity.Post;
import com.ott.onde.user.dto.UserJoinRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @Column(name = "user_idx")
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PreferGenre> preferGenres = new ArrayList<>();

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

    public User(Long userId) {
        this.userId = userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
