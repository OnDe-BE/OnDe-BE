package com.ott.ond.user.repository;

import com.ott.ond.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long userId);
    Optional<User> findById(String id);
    Optional<User> findByNicknameAndEmail(String name, String email);
    User findByNicknameAndEmailAndProvider(String name, String email, String provider);
    Optional<User> findUserByEmail(String email);
}
