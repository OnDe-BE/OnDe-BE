package com.ott.reelpick.user.service;

import com.ott.reelpick.user.User;
import com.ott.reelpick.user.dto.UserDTO;
import com.ott.reelpick.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Long join(UserDTO dto){
        User entity = dtoToEntity(dto);
        userRepository.save(entity);

        return entity.getUserId();
    }
}
