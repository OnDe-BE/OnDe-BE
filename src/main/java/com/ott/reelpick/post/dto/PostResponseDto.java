package com.ott.reelpick.post.dto;

import com.ott.reelpick.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostResponseDto {

    private Long post_idx;
    private Long user_idx;
    private String title;
    private String contents;
    private Integer post_views;
    private Integer board_id;
    private Integer like_count;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public PostResponseDto(Post entity) {
        this.post_idx = entity.getPost_idx();
        this.user_idx = entity.getUser().getUserId();
        this.title = entity.getTitle();
        this.contents = entity.getContents();
        this.post_views = entity.getPost_views();
        this.board_id = entity.getBoard_id();
        this.like_count = entity.getLike_count();
        this.createdAt = entity.getCreatedAt();
        this.modifiedAt = entity.getModifiedAt();
    }

    public static PostResponseDto from(Post entity) {
        return new PostResponseDto(entity);
    }
}
