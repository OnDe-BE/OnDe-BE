package com.ott.onde.content.service;

import com.ott.onde.content.entity.CategorySort;
import com.ott.onde.content.repository.CategorySortRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j@Service@RequiredArgsConstructor
public class ContentsServiceMethod {
    private final CategorySortRepository cateSortRepository;

    public Map<String, String> sentenceSorting(String sentence){
        List<CategorySort> cs = new ArrayList<>();

        for(String s : sentence.split(" ")){
            List<CategorySort> str = this.cateSortRepository.findByWord("%"+s+"%");

            cs.addAll(str);
        }

        Map<String, String> result = new HashMap<>();

        for(CategorySort c : cs){

            if(c.getCategoryCode().contains("PF")){
                if(!result.containsKey("platform")){
                    result.put("platform",c.getDbWord());
                }else{
                    result.put("platform", result.get("platform") + "|" + c.getDbWord());
                }
            }else{
                if(!result.containsKey("genre")){
                    result.put("genre",c.getDbWord());
                }else{
                    result.put("genre", result.get("genre") + "|" + c.getDbWord());
                }
            }
        }

        return result;
    }

    public PageRequest pagingRequestMethod(String orderCategory, int nowPage, int pageCount){
        Sort sort = switch (orderCategory) {
            case "rank" -> Sort.by(Sort.Direction.DESC, "hit_point");
            case "new" -> Sort.by(Sort.Direction.DESC, "released");
            default -> Sort.by(Sort.Direction.DESC, "title");
        };

        return PageRequest.of(nowPage, pageCount, sort);
    }

    public String sentenceSortingMethod(String sentence){
        return String.join("|",sentence.split("[ |,]"));
    }
}
