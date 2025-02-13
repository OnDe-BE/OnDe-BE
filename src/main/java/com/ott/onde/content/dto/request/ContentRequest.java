package com.ott.onde.content.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ContentRequest {
    private String contentId;
    private String title;
    private String age;
    private String contentImg;
    private int rank;

    @Builder
    public ContentRequest(String contentId, String title, String age, String contentImg, int rank) {
        this.contentId = contentId;
        this.title = title;
        this.age = age;
        this.contentImg = contentImg;
        this.rank = rank;
    }
}
