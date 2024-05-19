package com.ott.reelpick.post.repository;

import com.ott.reelpick.post.entity.Comment;
import com.ott.reelpick.post.entity.Likes;
import com.ott.reelpick.post.entity.Post;
import com.ott.reelpick.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByPostAndUser(Post post, User user);
    Optional<Likes> findByCommentAndUser(Comment comment, User user);

    void deleteAllByUser(User user);
}
