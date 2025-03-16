package com.ott.onde.post.entity;

import com.ott.onde.post.dto.PostRequestsDto;
import com.ott.onde.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@NoArgsConstructor
@Table(name = "post")
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_idx", updatable = false)
    private Long postIdx;

    @ManyToOne
    @JoinColumn(name = "user_code", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(name = "post_views")
    private Integer postViews;

    @Setter
    @Column(name = "like_count")
    private Integer likeCount;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private BoardKind boardKind;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Likes> likesList = new ArrayList<>();


    public Post(PostRequestsDto requestsDto, BoardKind boardKind, User user) {
        this.title = requestsDto.getTitle();
        this.contents = requestsDto.getContents();
        this.user = user;
        this.boardKind = boardKind;
        this.postViews = 0;
        this.likeCount = 0;
    }

    public void update(PostRequestsDto requestsDto, BoardKind boardKind, User user){
        this.title = requestsDto.getTitle();
        this.contents = requestsDto.getContents();
        this.user = user;
        this.boardKind = boardKind;
    }

}
