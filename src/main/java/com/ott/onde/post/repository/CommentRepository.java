package com.ott.onde.post.repository;

import com.ott.onde.post.entity.Comment;
import com.ott.onde.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByCommentIdxAndUser(Long commentIdx, User user);
    @Query(value = "SELECT c.*,COUNT(*) FROM post AS p, comment AS c WHERE c.post_idx = p.post_idx and c.post_idx = :postIdx",nativeQuery = true)
    Optional<Integer> findCountByPostId(@Param("postIdx") Long postId);
}
