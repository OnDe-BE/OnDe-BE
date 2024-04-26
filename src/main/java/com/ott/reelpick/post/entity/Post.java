package com.ott.reelpick.post.entity;

import com.ott.reelpick.post.dto.PostRequestsDto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;


@Getter
@Entity
@NoArgsConstructor
@Table(name = "post")
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long post_id;

    @Column(nullable = false)
    private Long user_idx;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = true)
    private Integer post_views;

    @Column(nullable = true)
    private Integer like_count;

    @Column(nullable = false)
    private Integer board_id;

    public Post(PostRequestsDto requestsDto) {
        this.title = requestsDto.getTitle();
        this.contents = requestsDto.getContents();
        this.user_idx = requestsDto.getUser_idx();
        this.board_id = requestsDto.getBoard_id();
        this.post_views = 0;
        this.like_count = 0;
    }

    public void update(PostRequestsDto requestsDto){
        this.title = requestsDto.getTitle();
        this.contents = requestsDto.getContents();
        this.user_idx = requestsDto.getUser_idx();
        this.board_id = requestsDto.getBoard_id();
    }

}