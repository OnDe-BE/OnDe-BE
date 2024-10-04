package com.ott.onde.post.dto;

import lombok.Data;

@Data
public class PostRequestsDto {
    private Integer boardId;
    private String title;
    private String contents;
}
