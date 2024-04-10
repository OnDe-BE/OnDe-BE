package com.ott.reelpick.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "user_idx", nullable = false, unique = true)
    private Long userIdx;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    public RefreshToken(Long userIdx, String refreshToken) {
        this.userIdx = userIdx;
        this.refreshToken = refreshToken;
    }

    public RefreshToken(String id, String refreshToekn) {
    }

    public RefreshToken update(String newRefreshToken) {
        this.refreshToken = newRefreshToken;

        return this;
    }
}
