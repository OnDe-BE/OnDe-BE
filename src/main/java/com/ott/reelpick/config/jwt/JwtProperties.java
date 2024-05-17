package com.ott.reelpick.config.jwt;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("jwt")
@RequiredArgsConstructor
public class JwtProperties {
    private String secretKey;
}
