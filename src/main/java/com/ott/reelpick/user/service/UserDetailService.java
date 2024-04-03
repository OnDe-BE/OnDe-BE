package com.ott.reelpick.user.service;

import com.ott.reelpick.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException((id)));
    }
}
