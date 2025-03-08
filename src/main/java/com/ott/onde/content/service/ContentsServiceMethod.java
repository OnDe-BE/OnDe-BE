package com.ott.onde.content.service;

import com.ott.onde.content.dto.request.ContentRequest;
import com.ott.onde.content.dto.response.ContentResponse;
import com.ott.onde.content.entity.CategorySort;
import com.ott.onde.content.repository.CategorySortRepository;
import com.ott.onde.content.repository.genre.ContentGenreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j@Service@RequiredArgsConstructor
public class ContentsServiceMethod {
    private final CategorySortRepository cateSortRepository;
    private final ContentGenreRepository contentGenreRepository;

    public Map<String, String> sentenceSorting(String sentence){
        Map<String, String> result = new HashMap<>();

        for(CategorySort c : this.sentenceDivideType(sentence)){
            result = this.sentenceToWord(c, result);
        }

        return result;
    }

    public List<CategorySort> sentenceDivideType(String sentence){
        List<CategorySort> cs = new ArrayList<>();



        for(String s : sentence.split(" ")){
            List<CategorySort> str = this.cateSortRepository.findByWord("%"+s+"%");

            cs.addAll(str);
        }

        return cs;
    }

    public Map<String, String> sentenceToWord(CategorySort c,Map<String, String> result){
        if(c.getCategoryCode().contains("PF")){
            result.put("platform", result.containsKey("platform") ? result.get("platform") + "|" + c.getDbWord() : c.getDbWord());
        }else{
            result.put("genre", result.containsKey("genre") ? result.get("genre") + "|" + c.getDbWord() : c.getDbWord());
        }

        return result;
    }

    public PageRequest pagingRequestMethod(String orderCategory, int nowPage, int pageCount){
        orderCategory = orderCategory == null ? "rank" : orderCategory;

        Sort sort = switch (orderCategory) {
            case "rank" -> Sort.by(Sort.Direction.DESC, "hit_point");
            case "new" -> Sort.by(Sort.Direction.DESC, "released");
            default -> Sort.by(Sort.Direction.DESC, "title");
        };

        return PageRequest.of(nowPage, pageCount, sort);
    }

//    public String sentenceSortingMethod(String sentence){
//        return String.join("|",sentence.split("[ |,]"));
//    }

    public List<String> findGenresByContentId(String contentId){
        return this.contentGenreRepository.findGenreByContentId(contentId);
    }

    public Page<ContentRequest> pageResponseToRequest(Page<ContentResponse> paging, int number){
        AtomicInteger rank = new AtomicInteger(number);

        return paging.map(x-> ContentRequest.builder().contentId(x.getContent_id())
                .title(x.getTitle())
                .age(x.getAge())
                .contentImg(x.getContent_img())
                .rank(rank.getAndIncrement())
                .genres(this.findGenresByContentId(x.getContent_id())).build());
    }
}
