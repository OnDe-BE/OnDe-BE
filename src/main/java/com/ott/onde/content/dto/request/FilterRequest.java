package com.ott.onde.content.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Data
@Getter@Setter
public class FilterRequest {
    private String age;
    private List<Integer> released;
    private String genre;
    private List<Integer> userAge;
    private String gender;
    private List<String> cType;
    private int nowPage;
    private int pageCount;

    @Builder
    public FilterRequest(List<String> age, List<Integer> released, List<String> genre, List<Integer> userAge, String gender, List<String> cType) {
        this.age = age == null ? "all|7|12|15|19|청불" : String.join("|",age);
        this.released = released == null ? List.of(1900, 2200) : released;
        this.genre = genre == null ? "" : String.join("|",genre);
        this.userAge = userAge;
        this.gender = gender;
        this.cType=cType == null ? List.of("movie|series") : cType;
    }

//    public Map<String, Boolean> getCType(String cType) {
//        boolean type = switch (cType) {
//
//            default -> false;
//        };
//
//        return Map.of(cType,type);
//    }
}
