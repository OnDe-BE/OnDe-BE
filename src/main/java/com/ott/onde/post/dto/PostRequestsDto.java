package com.ott.onde.post.dto;

import lombok.Data;

@Data
public class PostRequestsDto {
    private Integer boardid;
    private Long user_idx;
    private String id;
    private String title;
    private String contents;
}
