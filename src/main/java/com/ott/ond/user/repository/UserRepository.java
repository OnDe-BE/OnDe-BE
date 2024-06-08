package com.ott.ond.user.repository;

import com.ott.ond.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long userId);
    Optional<User> findById(String id);


    User findAllById(String id);
    User findByNicknameAndEmailAndProvider(String name, String email, String provider);
}
