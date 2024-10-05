//package com.ott.onde.security;
//
//import com.ott.onde.user.entity.User;
//import lombok.Getter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//
//public class UserDetailsImpl implements UserDetails {
//    private final User user;
//    private final String username;
//
//    // 인증이 완료된 사용자 추가하기
//    public UserDetailsImpl(User user, String username) {
//        this.user = user;
//        this.username = username;
//    }
//
//    public User user() {
//        return user;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
//    }
//
//    @Override
//    public String getPassword() {
//        return null;
//    }
//
//    @Override
//    public String getUsername() {
//        return this.username;
//    }
//
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return false;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return false;
//    }
//}
