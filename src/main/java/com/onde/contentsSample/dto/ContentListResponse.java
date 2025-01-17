package com.onde.contentsSample.dto;

import lombok.Builder;
import lombok.Data;

public interface ContentListResponse {
    String getContent_id();
    String getTitle();
    String getSummary();
    String getC_type();
    String getAge();
    String getReleased();
    String getContent_img();
    String getGenre();
}
