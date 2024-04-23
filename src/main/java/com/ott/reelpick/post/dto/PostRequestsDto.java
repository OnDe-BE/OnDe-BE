package com.ott.reelpick.post.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class PostRequestsDto {
    private Long user_idx;
    private String title;
    private String contents;
    private Integer board_id;
}
