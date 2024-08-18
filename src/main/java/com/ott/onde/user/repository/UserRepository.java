package com.ott.onde.user.repository;

import com.ott.onde.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long userId);
    Optional<User> findById(String id);

    User findAllById(String id);
    User findByNicknameAndEmailAndProvider(String name, String email, String provider);
    User findIdByEmailAndProvider(String email, String provider);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u SET u.password = :password WHERE u.email = :email")
    void updatePassword(String password, String email);

    @Modifying
    @Query(value = "DELETE FROM User u WHERE u.userId = :userId", nativeQuery = true)
    void deleteById(Long userId);
}
