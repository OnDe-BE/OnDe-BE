package com.ott.ond.post.repository;

import com.ott.ond.post.entity.Comment;
import com.ott.ond.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByCommentIdxAndUser(Long commentIdx, User user);
}
