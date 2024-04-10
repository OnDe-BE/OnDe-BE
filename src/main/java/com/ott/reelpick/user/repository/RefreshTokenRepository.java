package com.ott.reelpick.user.repository;

import com.ott.reelpick.user.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUserId(Long userIdx);
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
