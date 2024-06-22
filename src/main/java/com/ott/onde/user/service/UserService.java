package com.ott.onde.user.service;

import com.ott.onde.config.jwt.JwtTokenUtil;
import com.ott.onde.post.entity.Comment;
import com.ott.onde.user.dto.UserInfoResponse;
import com.ott.onde.user.entity.User;
import com.ott.onde.user.repository.UserRepository;
import com.ott.onde.util.ErrorCode;
import com.ott.onde.util.HospitalReviewAppException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.secret_key}")
    private String secretKey;
    private long expiredTimeMs = 1000 * 60 * 60; //1시간

    /**
     * 회원가입
     * @param user
     * @return
     */
    public User join(User user){
        userRepository.findById(user.getId())
                .ifPresent( user1 -> {
                    throw new HospitalReviewAppException(ErrorCode.DUPLICATED_USER_NAME, String.format("UserId : %s",user1.getUserId()));
                });
        userRepository.save(user);

        return user;
    }

    /**
     * 로그인
     * @param userId
     * @param password
     * @return
     */
    public String login(String userId, String password) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new HospitalReviewAppException(ErrorCode.USER_NOT_FOUNDED, String.format("%s는 가입된 적이 없습니다.", userId)));

        if(!encoder.matches(password,user.getPassword())){
            throw new HospitalReviewAppException(ErrorCode.INVALID_PASSWORD, String.format("userName 또는 password가 잘못 되었습니다."));
        }
        return JwtTokenUtil.createToken(userId, secretKey, Duration.ofDays(expiredTimeMs));
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
    public UserInfoResponse getInfo(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));
        UserInfoResponse userInfoResponse = new UserInfoResponse();
        userInfoResponse.setUserId(user.getId());
        userInfoResponse.setPassword(user.getPassword());
        userInfoResponse.setAge(user.getAge());
        userInfoResponse.setGender(user.getGender());
        userInfoResponse.setNickname(user.getNickname());
        userInfoResponse.setNationality(user.getNationality());
        userInfoResponse.setEmail(user.getEmail());
        return userInfoResponse;
    }

}

