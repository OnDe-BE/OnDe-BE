package com.ott.onde.config.oauth;

import com.ott.onde.config.jwt.TokenProvider;
import com.ott.onde.user.dto.OAuthLoginRequest;
import com.ott.onde.user.entity.RefreshToken;
import com.ott.onde.user.entity.User;
import com.ott.onde.user.repository.RefreshTokenRepository;
import com.ott.onde.user.repository.UserRepository;
import com.ott.onde.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${jwt.secret_key}")
    private String key;

    @Value("${app.oauth2.authorizedRedirectUri}")
    private String redirectUri;
    private String targetUrl;

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final OAuthAuthorizationRequestBasedOnCookieRepository authorizationRequestRepository;
    private final TokenProvider tokenProvider;

    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14);
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofDays(1);
//    public static final String REDIRECT_PATH = "";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {

        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String realName = null;
        String email = null;
        String provider = null;

        // 구글 인증시 아래 로직
        if(attributes.get("sub") != null) {
            realName = oAuth2User.getAttribute("name");
            email = oAuth2User.getAttribute("email");
            provider = "google";
            log.info("🌈 구글 인증 시 이름 추출 [{}] || 이메일 추출 [{}]", realName, email);
        }

        // 네이버 인증 시 아래 로직
        if(attributes.get("response") != null){
            Map<String, Object> response2 =(Map<String, Object>) attributes.get("response");
            realName = (String) response2.get("name");
            email = (String) response2.get("email");
            provider = "naver";
            log.info("🌈 네이버 인증 시 이름 추출 [{}] || 이메일 추출 [{}] || 프로바이더 추출[{}]",realName,email,provider);
        }

        // 카카오 인증 시 아래 로직
        if(attributes.get("id") != null){
            Map<String, Object> kakaoAccount =(Map<String, Object>) attributes.get("kakao_account");
            Map<String, String> profile = (Map)kakaoAccount.get("profile");

            realName = profile.get("nickname");
            email = (String) kakaoAccount.get("email");
            provider = "kakao";
            log.info("🌈 카카오 인증 시 이름 추출 [{}] || 이메일 추출 [{}]",realName,email);
        }

        // 이름과 이메일이 둘다 일치하는 회원이 저장되어있을 것
        User foundUser = userRepository.findByNicknameAndEmailAndProvider(realName, email, provider);
        String foundAccount = foundUser.getEmail();
        log.info("🌈 소셜 로그인 인증한 계정명 [{}]",foundAccount);

        OAuthLoginRequest oAuthLoginRequest = new OAuthLoginRequest();
        oAuthLoginRequest.setUsername(realName);
        oAuthLoginRequest.setId(foundUser.getId());
        oAuthLoginRequest.setIndex(foundUser.getUserId());

        // 회원 계정으로 토큰 생성 후 쿼리 파라미터로 보냄
        String refreshToken = tokenProvider.createToken(foundUser.getUserId(), String.valueOf(REFRESH_TOKEN_DURATION));
        saveRefreshToken(foundUser.getUserId(), refreshToken);
        addRefreshTokenToCookie(request, response, refreshToken);

        String accessToken = tokenProvider.createToken(foundUser.getUserId(), String.valueOf(ACCESS_TOKEN_DURATION));
        targetUrl = UriComponentsBuilder.fromUriString(redirectUri)
                .queryParam("token", accessToken)
                .build().toUriString();

        clearAuthenticationAttributes(request, response);
        response.sendRedirect("");
    }

    private void saveRefreshToken(Long userId, String newRefreshToken) {
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId)
                .map(entity -> entity.update(newRefreshToken))
                .orElse(new RefreshToken(userId, newRefreshToken));

        refreshTokenRepository.save(refreshToken);
    }

    private void addRefreshTokenToCookie(HttpServletRequest request, HttpServletResponse response, String refreshToken) {
        int cookieMaxAge = (int) REFRESH_TOKEN_DURATION.toSeconds();

        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN_COOKIE_NAME);
        CookieUtil.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, refreshToken, cookieMaxAge);
    }

    private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

//    private String getTargetUrl(String token){
//        return UriComponentsBuilder.fromUriString(REDIRECT_PATH)
//                .queryParam("token", token)
//                .build()
//                .toUriString();
//    }
}
