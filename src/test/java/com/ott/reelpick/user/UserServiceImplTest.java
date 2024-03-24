package com.ott.reelpick.user;

import com.ott.reelpick.user.dto.UserJoinDTO;
import com.ott.reelpick.user.repository.UserRepository;
import com.ott.reelpick.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    private UserService userService;

    @Test
    @DisplayName("일반 회원가입 테스트")
    void register() {
       UserJoinDTO userJoinDTO = new UserJoinDTO();
       userJoinDTO.setId("test");
       userJoinDTO.setPassword("test1234");
       userJoinDTO.setAge(25);
       userJoinDTO.setGender("m");
       userJoinDTO.setNickname("test");
       userJoinDTO.setNationality("korea");
       userJoinDTO.setEmail("test@test.com");
       userService.join(userJoinDTO);

    }
}
