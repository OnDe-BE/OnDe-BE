package com.ott.onde.post.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    String contents;
    Long parent_id;
}
