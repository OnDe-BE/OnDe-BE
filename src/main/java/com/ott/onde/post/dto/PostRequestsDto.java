package com.ott.onde.post.dto;

import lombok.Data;

@Data
public class PostRequestsDto {
    private Long user_idx;
    private String id;
    private String title;
    private String contents;
    private Integer boardid;
}
