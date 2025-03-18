package com.ott.onde.post.entity;

import com.ott.onde.post.dto.CommentRequestsDto;
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
    @JoinColumn(name = "user_code", nullable = false)
    private User user;


    @Column(nullable = false, length = 2000)
    private String contents;

    @Column(name = "parent_id")
    private Long parentId;

    @OrderBy("createdAt asc ")
    @OneToMany(mappedBy = "parentId", cascade = CascadeType.ALL)
    private List<Comment> childCommentList = new ArrayList<>();

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<Likes> likesList = new ArrayList<>();

    @Setter
    @Column(name = "like_count")
    private int likeCount;

    @Builder
    public Comment(Comment requestDto, Post post, User user) {
        this.contents = requestDto.getContents();
        this.parentId = requestDto.getParentId();
        this.post = post;
        this.user = user;
        this.likeCount = 0;
    }

    public Comment(CommentRequestsDto requestDto, Post post, User user) {
        this.contents = requestDto.getContents();
        this.parentId = requestDto.getParentId();
        this.post = post;
        this.user = user;
        this.likeCount = 0;
    }

    public void update(CommentRequestsDto requestDto, User user) {
        this.contents = requestDto.getContents();
        this.user = user;
    }

    public void addChildComment(Comment child) {
        this.getChildCommentList().add(child);
    }


}
