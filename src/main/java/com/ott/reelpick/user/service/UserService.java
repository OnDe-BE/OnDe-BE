package com.ott.reelpick.user.service;

import com.ott.reelpick.user.User;
import com.ott.reelpick.user.dto.UserDTO;

public interface UserService {
    Long join(UserDTO dto);

    default User dtoToEntity(UserDTO dto) {
        User entity = User.builder()
                .id(dto.getId())
                .password(dto.getPassword())
                .age(dto.getAge())
                .gender(dto.getGender())
                .nickname(dto.getNickname())
                .nationality(dto.getNationality())
                .email(dto.getEmail())
                .build();
        return entity;
    }
}
