package com.ott.onde.post.repository;

import com.ott.onde.post.entity.Comment;
import com.ott.onde.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByCommentIdxAndUser(Long commentIdx, User user);
}
