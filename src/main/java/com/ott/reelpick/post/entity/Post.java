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

    @Column(nullable = false, name = "board_id")
    private Integer boardId;

    public Post(PostRequestsDto requestsDto) {
        this.title = requestsDto.getTitle();
        this.contents = requestsDto.getContents();
        this.user_idx = requestsDto.getUser_idx();
        this.boardId = requestsDto.getBoard_id();
    }

}