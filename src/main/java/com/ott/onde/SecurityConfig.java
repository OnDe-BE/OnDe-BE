package com.ott.onde;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@EnableWebSecurity //@Configuration 어노테이션 포함 및 보안 기능 활성화
public class SecurityConfig {
    //Http Security를 설정한다.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic().disable() // 기본 ui 사용, 사용하지 않을 시 disable()
                .csrf().disable() // REST API에서 csrf 보안이 필요없기 때문에 비활성화
                .cors().and()
                .authorizeRequests() // 요청에 대한 사용 권한을 체크
                .requestMatchers("/api/**").permitAll() // /api/** 리소스 접근을 인증 절차 없이 허용
                .requestMatchers("/api/v1/users/join", "/api/v1/users/login").permitAll() // 특정 리소스 접근을 인증 절차 없이 허용
                .anyRequest().authenticated() // 나머지 요청은 인증 필요
                .and()
                .formLogin()
                .successHandler(authenticationSuccessHandler()) // 로그인 성공 핸들러 설정
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // STATELESS로 설정
                .and()
                .build();
    }

    @Bean
    public SimpleUrlAuthenticationSuccessHandler authenticationSuccessHandler() {
        SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler();
        successHandler.setDefaultTargetUrl("/"); // 로그인 성공 후 리디렉션할 URL 설정
        return successHandler;
    }
}
