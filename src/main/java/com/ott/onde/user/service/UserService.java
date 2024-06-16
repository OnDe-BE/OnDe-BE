package com.ott.onde.user.service;

import com.ott.onde.config.jwt.JwtTokenUtil;
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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.secret_key}")
    private String secretKey;
    private long expiredTimeMs = 1000 * 60 * 60; //1시간

    public User join(User user){
        userRepository.findById(user.getId())
                .ifPresent( user1 -> {
                    throw new HospitalReviewAppException(ErrorCode.DUPLICATED_USER_NAME, String.format("UserId : %s",user1.getUserId()));
                });
        userRepository.save(user);

        return user;
    }
    public String login(String userId, String password) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new HospitalReviewAppException(ErrorCode.USER_NOT_FOUNDED, String.format("%s는 가입된 적이 없습니다.", userId)));

        if(!encoder.matches(password,user.getPassword())){
            throw new HospitalReviewAppException(ErrorCode.INVALID_PASSWORD, String.format("userName 또는 password가 잘못 되었습니다."));
        }
        return JwtTokenUtil.createToken(userId, secretKey, Duration.ofDays(expiredTimeMs));
    }
}

