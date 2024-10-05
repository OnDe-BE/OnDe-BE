package com.ott.onde.genre.dto;

import lombok.Data;

@Data
public class JoinPreferGenreRequest {
    private String genreId;
    private Long userId;
}
