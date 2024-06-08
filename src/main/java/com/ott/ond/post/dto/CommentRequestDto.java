package com.ott.ond.post.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    String contents;
    Long parent_id;
}
