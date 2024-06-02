package com.ott.reelpick.user.repository;

import com.ott.reelpick.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long userId);
    Optional<User> findById(String id);
    Optional<User> findByNicknameAndEmail(String name, String email);
    User findByNicknameAndEmailAndProvider(String name, String email, String provider);
    Optional<User> findUserByEmail(String email);
    List<User> findAllByEmail(String email);
}
