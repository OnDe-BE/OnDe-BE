package com.ott.reelpick.user.service;

import com.ott.reelpick.config.oauth.OAuthAttributes;
import com.ott.reelpick.config.oauth.OAuthProfile;
import com.ott.reelpick.user.entity.User;
import com.ott.reelpick.user.repository.UserRepository;
import com.ott.reelpick.util.DomainExtract;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest,OAuth2User> delegate =
                new DefaultOAuth2UserService();

        // 소셜에서 인증받아서 가져온 유저 정보를 담고 있다.
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 어떤 서비스인지(구글, 네이버 등등) -> 로그 찍어보면 naver 혹은 google 이 나온다.
        String registrationId = userRequest.getClientRegistration()
                .getRegistrationId();


        // OAuth2 로그인 진행 시 키가 되는 필드 값, 구글은 sub 네이버는 response 라는 이름을 갖는다.
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        Map<String, Object> attributes = oAuth2User.getAttributes();
        //서비스 마다 다른 키와 벨류 값을 변환하여 객체를 만든다.
        OAuthProfile oAuthProfile = OAuthAttributes.extract(registrationId, attributes);
        oAuthProfile.setProvider(registrationId);

        log.info("🌈 소셜 인증한 서비스 [{}]", oAuthProfile.getProvider());
        log.info("🌈 이름 [{}]", oAuthProfile.getName());
        log.info("🌈 이메일 [{}]",oAuthProfile.getEmail());

        // 기존에 있는 유저인지 체크
        User check = check(oAuthProfile);

        // 없을 경우 신규 가입
        if(check == null) {
            User user = new User();
            user.setNickname(oAuthProfile.getName());
            user.setEmail(oAuthProfile.getEmail());
            user.setProvider(oAuthProfile.getProvider());
            SaveUser(user);
        }

        // 해당 계정이 갖고 있는 권한 그대로 주입
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("user")),
                attributes,
                userNameAttributeName);

    }

    // 유저가 없으면 유저 생성
    public void SaveUser(User userProfile) {
        userRepository.save(userProfile);
    }

    // 소셜 로그인 시, 소셜 로그인에 등록된 실명과 이메일로 가입한 회원이 존재하지 않을 경우 null
    private User check(OAuthProfile userProfile) {
        String provider = DomainExtract.extractDomain(userProfile.getEmail());
        User user = userRepository.findByNicknameAndEmailAndProvider(userProfile.getName(), userProfile.getEmail(), userProfile.getProvider());
        if (user != null) {
            return user;
        } else {
            return null;
        }
    }

}
