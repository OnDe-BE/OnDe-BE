package com.ott.onde.content.dto.result;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data@Getter@Setter
public class ContentDetailResult {
    private String contentId;
    private String title;
    private String summary;
    private String cType;
    private String age;
    private String released;
    private String contentImg;
    private List<String> genres;

    @Builder
    public ContentDetailResult(String contentId, String title, String summary, String cType, String age, String released, String contentImg, List<String> genres) {
        this.contentId = contentId;
        this.title = title;
        this.summary = summary;
        this.cType = cType;
        this.age = age;
        this.released = released;
        this.contentImg = contentImg;
        this.genres = genres;
    }
}
