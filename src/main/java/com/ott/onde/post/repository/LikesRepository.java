package com.ott.onde.post.repository;

import com.ott.onde.post.entity.Comment;
import com.ott.onde.post.entity.Likes;
import com.ott.onde.post.entity.Post;
import com.ott.onde.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByPostAndUser(Post post, User user);
    Optional<Likes> findByCommentAndUser(Comment comment, User user);

    void deleteAllByUser(User user);
}
