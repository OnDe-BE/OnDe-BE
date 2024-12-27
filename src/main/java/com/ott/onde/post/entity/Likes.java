package com.ott.onde.post.entity;

import com.ott.onde.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "likes")
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long likeId;

    @ManyToOne
    @JoinColumn(name = "post_idx")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "comment_idx")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "user_idx", nullable = false)
    private User user;


    @Builder
    public Likes(Post post, Comment comment, User user) {
        this.post = post;
        this.comment = comment;
        this.user = user;
    }


    public static Likes of(Post post, User user) {
        Likes likes = Likes.builder()
                .post(post)
                .user(user)
                .comment(null)
                .build();
        post.getLikesList().add(likes);
        return likes;
    }

    public static Likes of(Comment comment, User user) {
        Likes likes = Likes.builder()
                .post(null)
                .comment(comment)
                .user(user)
                .build();
        comment.getLikesList().add(likes);
        return likes;
    }




}
