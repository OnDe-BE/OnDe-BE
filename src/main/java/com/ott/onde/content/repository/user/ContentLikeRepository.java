package com.ott.onde.content.repository.user;

import com.ott.onde.content.entity.user.ContentLike;
import com.ott.onde.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContentLikeRepository extends JpaRepository<ContentLike, Long> {
    List<ContentLike> findByUserOrderByCreatedAt(User user);
    Optional<ContentLike> findByUserAndContentId(User user, String contentId);
}

