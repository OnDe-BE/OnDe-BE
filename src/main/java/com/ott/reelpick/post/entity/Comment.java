package com.ott.reelpick.post.entity;

import com.ott.reelpick.post.dto.CommentRequestDto;
import com.ott.reelpick.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;


import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comment")
public class Comment extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comment_id;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
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

    @Column
    private int like_count;

    @Builder
    public Comment(CommentRequestDto requestDto, Post post, User user) {
        this.contents = requestDto.getContents();
        this.parent_id = requestDto.getParent_id();
        this.post = post;
        this.user = user;
        this.like_count = 0;
    }

    public void update(CommentRequestDto requestDto, User user) {
        this.contents = requestDto.getContents();
        this.user = user;
    }

    public void addChildComment(Comment child) {
        this.getChildCommentList().add(child);
    }



}
