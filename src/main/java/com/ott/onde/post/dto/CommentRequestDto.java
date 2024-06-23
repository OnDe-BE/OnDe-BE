package com.ott.onde.post.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private String contents;
    private String id;
    private Long parent_id;
}
