package com.ott.reelpick.user.service;

import com.ott.reelpick.user.entity.User;
import com.ott.reelpick.user.dto.UserJoinDTO;
import com.ott.reelpick.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService  {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long join(UserJoinDTO dto){
        return userRepository.save(User.builder()
                .id(dto.getId())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .age(dto.getAge())
                .gender(dto.getGender())
                .nickname(dto.getNickname())
                .nationality(dto.getNationality())
                .email(dto.getEmail())
                .build()).getUserId();
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

}
