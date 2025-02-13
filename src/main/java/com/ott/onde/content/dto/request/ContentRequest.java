package com.ott.onde.content.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class ContentRequest {
    private String contentId;
    private String title;
    private String age;
    private String contentImg;
    private List<String> genres;
    private int rank;

    @Builder
    public ContentRequest(String contentId, String title, String age, String contentImg,List<String> genres, int rank) {
        this.contentId = contentId;
        this.title = title;
        this.age = age;
        this.contentImg = contentImg;
        this.genres = genres;
        this.rank = rank;
    }
}
