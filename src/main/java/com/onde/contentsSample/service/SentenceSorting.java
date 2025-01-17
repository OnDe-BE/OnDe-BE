package com.onde.contentsSample.service;

import com.onde.contentsSample.dto.util.SearchResponse;
import com.onde.contentsSample.entity.CategorySort;
import com.onde.contentsSample.repository.CategorySortRepository;
import com.onde.contentsSample.service.util.SentenceSortData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.*;

@Slf4j@Service@RequiredArgsConstructor
public class SentenceSorting {
    private final SentenceSortData sentenceSortData;
    private final CategorySortRepository cateSortRepository;

    public Map<String, String> sentenceSorting(String sentence){
        String platforms = "";
        String genres = "";

        for(String s : sentence.split("^[,/]|\\s$")){
            List<CategorySort> cs = this.cateSortRepository.findByWord("%"+s+"%");

            for(CategorySort c : cs){
                if(c.getCategoryCode().matches("PF")){
                    platforms = String.join("|",c.getDbWord());
                }else if (c.getCategoryCode().matches("GR")){
                    genres = String.join("|",c.getDbWord());
                }
            }
        }
        Map<String, String> result = new HashMap<>();

        if(!platforms.isBlank()){

            result.put("platform",platforms);
//            sr.add(SearchResponse.builder().type("platform").data(platforms).build());
        }

        if(!genres.isBlank()){
            result.put("genre",genres);
//            sr.add(SearchResponse.builder().type("genre").data(genres).build());
        }

        return result;
    }

    public void sortingData(){

    }
}
