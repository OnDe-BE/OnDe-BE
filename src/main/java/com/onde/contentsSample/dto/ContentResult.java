package com.onde.contentsSample.dto;

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

//    @Builder
//    public ContentResult(String contentId, String title, String summary, String cType, int age, String contentImg, List<String> genre){
//        return ContentResult.builder().contentId(contentId)
//                .title(title)
//                .summary(summary)
//                .cType(cType)
//                .age(age)
//                .contentImg(contentImg)
//                .genre(genre).build();
//    }
}
