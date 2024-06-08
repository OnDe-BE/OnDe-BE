package com.ott.ond.post.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class PostRequestsDto {
    private Long user_idx;
    private String id;
    private String title;
    private String contents;
    private Integer boardid;
}
