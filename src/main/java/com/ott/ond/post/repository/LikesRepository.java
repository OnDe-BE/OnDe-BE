package com.ott.ond.post.repository;

import com.ott.ond.post.entity.Comment;
import com.ott.ond.post.entity.Likes;
import com.ott.ond.post.entity.Post;
import com.ott.ond.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByPostAndUser(Post post, User user);
    Optional<Likes> findByCommentAndUser(Comment comment, User user);

    void deleteAllByUser(User user);
}
