package com.ott.reelpick.post.dto;

import com.ott.reelpick.post.entity.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CommentResponseDto {

    private Long comment_id;

    private Long post_id;
    private String contents;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Integer like_count;
    private List<CommentResponseDto> childCommentList;

    @Builder
    private CommentResponseDto(Comment entity) {
        this.comment_id = entity.getComment_id();
        this.post_id = entity.getPost().getPost_id();
        this.contents = entity.getContents();
        this.username = entity.getUser().getUsername();
        this.createdAt = entity.getCreatedAt();
        this.modifiedAt = entity.getModifiedAt();
        this.like_count = entity.getLike_count();
        this.childCommentList = entity.getChildCommentList().stream().map(CommentResponseDto::from).toList();
    }

    public static CommentResponseDto from(Comment entity) {
        return CommentResponseDto.builder()
                .entity(entity)
                .build();
    }
}
