package com.ott.onde.content.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data@Builder
public class ContentResult {
    private String contentId;
    private String title;
    private String summary;
    private String released;
    private String cType;
    private String age;
    private String contentImg;
    private List<String> genre;

}
