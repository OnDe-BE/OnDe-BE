package com.ott.reelpick.post.dto;

import com.ott.reelpick.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostResponseDto {

    private Long post_id;
    private Long user_idx;
    private String title;
    private String contents;
    private Integer views;
    private Integer board_id;
    private LocalDateTime createdAt;

    public PostResponseDto(Post entity) {
        this.post_id = entity.getPost_id();
        this.user_idx = entity.getUser_idx();
        this.title = entity.getTitle();
        this.contents = entity.getContents();
        this.views = entity.getViews();
        this.board_id = entity.getBoard_id();
        this.createdAt = entity.getCreatedAt();
    }
}
