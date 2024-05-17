package com.ott.reelpick.config;

import com.ott.reelpick.config.oauth.OAuthFailureHandler;
import com.ott.reelpick.config.oauth.OAuthSuccessHandler;
import com.ott.reelpick.user.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
public class WebOAuthSecurityConfig {
    private final OAuthSuccessHandler oauthSuccessHandler;
    private final OAuthFailureHandler oauthfailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers("/img/**", "/css/**", "/js/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .httpBasic().disable()
                .logout().disable();

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .requestMatchers("/api/token", "/users/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/**").authenticated()
                .anyRequest().permitAll();

        http.oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(customOAuth2UserService)
                .and()
                .successHandler(oauthSuccessHandler)
                .failureHandler(oauthfailureHandler);

        http.logout()
                .logoutUrl("/users/logout")
                .deleteCookies("refresh_token")
                .logoutSuccessUrl("/");


        http.exceptionHandling()
                .defaultAuthenticationEntryPointFor(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                        new AntPathRequestMatcher("/api/**"));


        return http.build();
    }
}
