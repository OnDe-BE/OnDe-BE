package com.ott.onde.post.dto;

import com.ott.onde.post.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostResponseDto {

    private Long postIdx;
    private String userCode;
    private String title;
    private String contents;
    private Integer postViews;
    private Integer boardId;
    private Integer likeCount;
    private Integer commentCount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public PostResponseDto(Post entity) {
        this.postIdx = entity.getPostIdx();
        this.userCode = entity.getUser().getUserCode();
        this.title = entity.getTitle();
        this.contents = entity.getContents();
        this.postViews = entity.getPostViews();
        this.boardId = entity.getBoardKind().getBoardId();
        this.likeCount = entity.getLikeCount();
        this.createdAt = entity.getCreatedAt();
        this.modifiedAt = entity.getModifiedAt();
    }

    public PostResponseDto setCommentCount(PostResponseDto entity, Integer commentCount) {
        entity.commentCount = commentCount;
        return entity;
    }

    public static PostResponseDto from(Post entity) {
        return new PostResponseDto(entity);
    }
}
