package com.ott.onde.post.dto;

import lombok.Data;

@Data
public class PostRequestsDto {
    private Integer boardid;
    private String id;
    private String title;
    private String contents;
}
