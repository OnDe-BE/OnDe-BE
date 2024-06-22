package com.ott.onde.post.entity;

import com.ott.onde.post.dto.CommentRequestDto;
import com.ott.onde.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;


import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comment")
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_idx")
    private Long commentIdx;

    @ManyToOne
    @JoinColumn(name = "post_idx", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_idx", nullable = false)
    private User user;


    @Column(nullable = false, length = 2000)
    private String contents;

    @Column
    private Long parent_id;

    @OrderBy("createdAt asc ")
    @OneToMany(mappedBy = "parent_id", cascade = CascadeType.ALL)
    private List<Comment> childCommentList = new ArrayList<>();

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<Likes> likesList = new ArrayList<>();

    @Setter
    @Column
    private int like_count;

    @Builder
    public Comment(Comment requestDto, Post post, User user) {
        this.contents = requestDto.getContents();
        this.parent_id = requestDto.getParent_id();
        this.post = post;
        this.user = user;
        this.like_count = 0;
    }

    public Comment(CommentRequestDto requestDto, Post post, User user) {
        super();
    }

    public void update(CommentRequestDto requestDto, User user) {
        this.contents = requestDto.getContents();
        this.user = user;
    }

    public void addChildComment(Comment child) {
        this.getChildCommentList().add(child);
    }


}
