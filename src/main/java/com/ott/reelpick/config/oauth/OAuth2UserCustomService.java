package com.ott.reelpick.config.oauth;

import com.ott.reelpick.user.entity.User;
import com.ott.reelpick.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Service
public class OAuth2UserCustomService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest); // ❶ 요청을 바탕으로 유저 정보를 담은 객체 반환
        saveOrUpdate(user);

        return user;
    }

    // ❷ 유저가 있으면 업데이트, 없으면 유저 생성
    private User saveOrUpdate(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = (String) attributes.get("email");
        String nickname = (String) attributes.get("name");

        log.info("아이디 : " + email);
        log.info("닉네임 : " + nickname);

        User user = (User) userRepository.findById(email)
                .map(entity -> entity.update(nickname))
                .orElse(User.builder()
                        .id(email)
                        .nickname(nickname)
                        .build());

        return userRepository.save(user);
    }
}
