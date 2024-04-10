package com.ott.reelpick.post.dto;

import lombok.Getter;

@Getter
public class PostRequestsDto {
    private Long user_idx;
    private String title;
    private String contents;
    private Integer board_id;
}
