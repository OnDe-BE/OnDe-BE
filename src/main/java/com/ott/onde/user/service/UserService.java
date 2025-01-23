package com.ott.onde.user.service;

import com.ott.onde.genre.entity.InnerGenre;
import com.ott.onde.genre.entity.PreferGenre;
import com.ott.onde.genre.repository.InnerGenreRepository;
import com.ott.onde.global.oauth2.model.SecurityUser;
import com.ott.onde.user.dto.UserInfoResponse;
import com.ott.onde.user.dto.UserJoinRequest;
import com.ott.onde.user.entity.User;
import com.ott.onde.user.repository.UserRepository;
import com.ott.onde.global.oauth2.util.ErrorCode;
import com.ott.onde.global.oauth2.util.HospitalReviewAppException;
import com.ott.onde.global.oauth2.util.RandomTag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final InnerGenreRepository innerGenreRepository;
    private final PasswordEncoder encoder;

    /**
     * 회원가입
     * @param userJoinRequest
     * @return
     */
    @Transactional
    public User join(UserJoinRequest userJoinRequest){
        User user = userJoinRequest.toEntity(encoder.encode(userJoinRequest.getPassword()));
        user.setUserId(RandomTag.createHashtag());

        List<PreferGenre> preferGenres = new ArrayList<>();
        for (Long genreId : userJoinRequest.getPreferGenreList()) {
            InnerGenre genre = innerGenreRepository.findById(String.valueOf(genreId)).orElseThrow(
                    () -> new IllegalArgumentException("Invalid genre ID: " + genreId)
            );
            PreferGenre preferGenre = new PreferGenre();
            preferGenre.setUser(user);
            preferGenre.setInnerGenre(genre);
            preferGenres.add(preferGenre);
        }
        user.setPreferGenres(preferGenres);

        // User 저장 (Cascade로 PreferGenre도 자동 저장)
        userRepository.save(user);
        return user;
    }

    /**
     * 로그인
     * @param userId
     * @param password
     * @return
     */
    @Transactional
    public UserDetails login(String userId, String password) throws UsernameNotFoundException{
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new HospitalReviewAppException(ErrorCode.USER_NOT_FOUNDED, String.format("%s는 가입된 적이 없습니다.", userId)));

        if(!encoder.matches(password,user.getPassword())){
            throw new HospitalReviewAppException(ErrorCode.INVALID_PASSWORD, String.format("userName 또는 password가 잘못 되었습니다."));
        }
        return new SecurityUser(user);
    }

    /**
     * OAuth2 로그인
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username).get();
        return new SecurityUser(user);
    }

    @Transactional(readOnly = true)
    public User findById(Long userId) {
        return userRepository.findById(userId)
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
    public UserInfoResponse getInfo(Long id) {
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

