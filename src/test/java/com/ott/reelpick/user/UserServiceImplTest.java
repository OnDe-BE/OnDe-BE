package com.ott.reelpick.user;

import com.ott.reelpick.user.dto.UserDTO;
import com.ott.reelpick.user.repository.UserRepository;
import com.ott.reelpick.user.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserRepository userRepository;

    @Test
    void register() {
        UserDTO userDTO = UserDTO.builder()
                .id("test")
                .password("test")
                .age(15)
                .gender("남")
                .nickname("test")
                .nationality("북한")
                .email("dbstndh12@naver.com")
                .build();
        User user = userService.dtoToEntity(userDTO);
        userRepository.save(user);
    }
}
