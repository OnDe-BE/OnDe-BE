package com.onde.contentsSample.service;

import com.onde.contentsSample.dto.util.SearchResponse;
import com.onde.contentsSample.service.util.PlatformSortData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RequiredArgsConstructor@Service@Slf4j
public class CategorySort {
    private final PlatformSortData platformSortData;

    public List<SearchResponse> categorySort(String searchText){
        List<String> platformList = new ArrayList<>(List.of("넷플릭스","넷플", "티빙", "왓챠", "netflix", "tving", "watcha"));

        List<String> searches = Arrays.stream(searchText.split(" ")).toList();
        List<String> platforms = searches.stream().filter(o -> platformList.stream().anyMatch(Predicate.isEqual(o))).collect(Collectors.toList());
        searches = searches.stream().filter(o -> platformList.stream().noneMatch(Predicate.isEqual(o))).collect(Collectors.toList());

        List<SearchResponse> result = new ArrayList<>();

        if(!platforms.isEmpty()){
            platforms = platforms.stream().map(platformSortData::platformSorting).toList();

            result.add(SearchResponse.builder().type("platform").data(String.join("|",platforms)).build());
        }

        result.add(SearchResponse.builder()
                .type("category").data(String.join("|",searches)).build());

        return result;
    }

    public String sentenceSortToCategory(String sentence){
        String [] sentences = sentence.split(" ");

        return "";
    }
}
