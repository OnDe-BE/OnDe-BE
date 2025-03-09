package com.ott.onde.user.repository;

import com.ott.onde.user.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUserCode(String userId);
    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    /* userid에 해당하는 refresh_token 맟 access 토큰 삭제 */
    @Modifying
    @Query(value = "DELETE FROM RefreshToken r WHERE r.userId = :userId", nativeQuery = true)
    void deleteById(String userId);
}
