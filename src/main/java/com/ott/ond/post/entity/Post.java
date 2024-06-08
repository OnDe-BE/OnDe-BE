package com.ott.ond.post.entity;

import com.ott.ond.post.dto.PostRequestsDto;
import com.ott.ond.user.entity.User;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Getter
@Entity
@NoArgsConstructor
@Table(name = "post")
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_idx", updatable = false)
    private Long post_idx;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = true)
    private Integer post_views;

    @Column(nullable = true)
    private Integer like_count;

    @Column(nullable = false)
    private Integer boardid;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Likes> likesList = new ArrayList<>();


    public Post(PostRequestsDto requestsDto, User user) {
        this.title = requestsDto.getTitle();
        this.contents = requestsDto.getContents();
        this.user = user;
        this.boardid = requestsDto.getBoardid();
        this.post_views = 0;
        this.like_count = 0;
    }

    public void update(PostRequestsDto requestsDto, User user){
        this.title = requestsDto.getTitle();
        this.contents = requestsDto.getContents();
        this.user = user;
        this.boardid = requestsDto.getBoardid();
    }
}