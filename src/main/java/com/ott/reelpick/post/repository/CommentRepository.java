package com.ott.reelpick.post.repository;

import com.ott.reelpick.post.entity.Comment;
import com.ott.reelpick.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByCommentIdxAndUser(Long commentIdx, User user);
}
