package com.ott.onde.post.dto;

import lombok.Getter;

@Getter
public class CommentRequestsDto {
    private String contents;
    private Long parentId;
}
