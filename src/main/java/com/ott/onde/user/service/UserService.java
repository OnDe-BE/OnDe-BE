package com.ott.onde.user.service;

import com.ott.onde.config.jwt.GlobalResDTO;
import com.ott.onde.config.jwt.JwtTokenDTO;
import com.ott.onde.config.jwt.TokenProvider;
import com.ott.onde.content.entity.InnerGenre;
import com.ott.onde.content.entity.PreferGenre;
import com.ott.onde.content.entity.PreferSentence;
import com.ott.onde.content.repository.genre.InnerGenreRepository;
import com.ott.onde.content.repository.genre.PreferGenreRepository;
import com.ott.onde.content.repository.genre.PreferSentenceRepository;
import com.ott.onde.user.dto.UserInfoResponse;
import com.ott.onde.user.dto.UserJoinRequest;
import com.ott.onde.user.dto.UserLoginResponse;
import com.ott.onde.user.entity.RefreshToken;
import com.ott.onde.user.entity.User;
import com.ott.onde.user.repository.RefreshTokenRepository;
import com.ott.onde.user.repository.UserRepository;
import com.ott.onde.util.ErrorCode;
import com.ott.onde.util.HospitalReviewAppException;
import com.ott.onde.util.RandomTag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PreferGenreRepository preferGenreRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PreferSentenceRepository preferSentenceRepository;
    private final BCryptPasswordEncoder encoder;
    private final TokenProvider tokenProvider;

    @Value("${jwt.secret_key}")
    private String secretKey;
    private long expiredTimeMs = 1000 * 60 * 60; //1시간

    /**
     * 회원가입
     * @param userJoinRequest
     * @return
     */
    @Transactional
    public User join(UserJoinRequest userJoinRequest){
        User user = userJoinRequest.toEntity(encoder.encode(userJoinRequest.getPassword()));
        user.setUserCode(user.getNickname() + "#" + RandomTag.createHashtag());

        List<PreferGenre> preferGenres = new ArrayList<>();
        List<PreferSentence> preferSentences = new ArrayList<>();

        for (String genre : userJoinRequest.getPreferGenreList()) {
            PreferGenre preferGenre = new PreferGenre();

            preferGenre.setUser(user);
            preferGenre.setGenre(genre);

            preferGenreRepository.save(preferGenre);

            preferGenres.add(preferGenre);
        }

        for(String sentence : userJoinRequest.getPreferSentenceList()){
            PreferSentence preferSentence = new PreferSentence();

            preferSentence.setUser(user);
            preferSentence.setPreferSentence(sentence);

            preferSentenceRepository.save(preferSentence);

            preferSentences.add(preferSentence);
        }

        // User 저장 (Cascade로 PreferGenre도 자동 저장)
        userRepository.save(user);
        return user;
    }

    // 회원가입 시 선호 장르 저장
//    @Transactional
//    public void addPreferGenre(PreferGenre preferGenre, Long userId){
//
//    }

    /**
     * 로그인
     * @param userId
     * @param password
     * @return
     */
    @Transactional
    public GlobalResDTO login(String userId, String password, HttpServletResponse response) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new HospitalReviewAppException(
                        ErrorCode.UNAUTHORIZED,  // 이미 HttpStatus.UNAUTHORIZED 포함
                        "아이디 혹은 패스워드가 틀렸습니다."
                ));

        if (!encoder.matches(password, user.getPassword())) {
            throw new HospitalReviewAppException(
                    ErrorCode.UNAUTHORIZED,
                    "아이디 혹은 패스워드가 틀렸습니다."
            );
        }

        // JWT 토큰 생성
        String userIdLong = user.getUserCode();
        JwtTokenDTO jwtTokenDTO = tokenProvider.createAllToken(userIdLong);

        // RefreshToken 처리
        refreshTokenRepository.findByUserCode(userIdLong)
                .ifPresentOrElse(
                        existingToken -> refreshTokenRepository.save(existingToken.update(jwtTokenDTO.getRefreshToken())),
                        () -> refreshTokenRepository.save(new RefreshToken(userIdLong, jwtTokenDTO.getRefreshToken()))
                );

        // LoginResponseDTO 생성 (Access Token과 Refresh Token 포함)
        UserLoginResponse loginResponseDTO = new UserLoginResponse(
                jwtTokenDTO.getAccessToken(),
                jwtTokenDTO.getRefreshToken()
        );

        // 로그인 후 Refresh Token을 HttpOnly 쿠키로 저장
        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", jwtTokenDTO.getRefreshToken())
                .httpOnly(true) // JavaScript에서 접근 못 하도록 설정
                .secure(true)   // HTTPS에서만 접근 가능하도록 설정
                .path("/")      // 전체 도메인에서 접근 가능
                .maxAge(60 * 60 * 24 * 7) // 7일 동안 유효
                .domain(".ondemandia.com")  // 최상위 도메인 설정
                .sameSite("None")
                .build();

        // 쿠키를 응답에 추가
        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());


        // GlobalResDTO 생성
        GlobalResDTO responseDTO = new GlobalResDTO(
                "Success Login", // 성공 메시지
                HttpStatus.OK.value(), // 상태 코드
                loginResponseDTO // data로 loginResponseDTO 포함
        );

        return responseDTO;

    }

    @Transactional(readOnly = true)
    public User findById(String userId) {
        return userRepository.findByUserCode(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));
    }

    /**
     * 아이디 찾기
     * @param email
     * @return
     */
    public String findId(String email){
        User user = userRepository.findIdByEmailAndProvider(email, null);
        String id = user.getId();
        return id;
    }

    /**
     * 비밀번호 찾기(임시 비밀번호 변경)
     * @param code
     * @param email
     */
    public void updateByTemporarilyPassword(String code, String email){
        code = encoder.encode(code);
        userRepository.updatePassword(code, email);
    }
    /**
     * 사용자 정보 불러오기
     *
     * @param id
     * @return
     */
    public UserInfoResponse getInfo(String id) {
        UserInfoResponse userInfoResponse = new UserInfoResponse();
        Optional<User> user = userRepository.findById(id);
        userInfoResponse.setUserId(user.get().getId());
        userInfoResponse.setAge(user.get().getAge());
        userInfoResponse.setGender(user.get().getGender());
        userInfoResponse.setNickname(user.get().getNickname());
        userInfoResponse.setNationality(user.get().getNationality());
        userInfoResponse.setEmail(user.get().getEmail());
        return userInfoResponse;
    }
}

