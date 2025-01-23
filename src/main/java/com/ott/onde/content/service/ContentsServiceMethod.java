package com.ott.onde.content.service;

import com.ott.onde.content.entity.CategorySort;
import com.ott.onde.content.repository.CategorySortRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j@Service@RequiredArgsConstructor
public class ContentsServiceMethod {
    private final CategorySortRepository cateSortRepository;

    public Map<String, String> sentenceSorting(String sentence){
        List<CategorySort> cs = new ArrayList<>();

        for(String s : sentence.split("\\|")){
            cs.addAll(this.cateSortRepository.findByWord("%"+s+"%"));
        }

        List<CategorySort> pfs = cs.stream().filter(x -> x.getCategoryCode().matches("PF")).toList();
        List<CategorySort> grs = cs.stream().filter(x -> x.getCategoryCode().matches("GR")).toList();

        Map<String, String> result = new HashMap<>();

        if(!pfs.isEmpty()){
            result.put("platform",String.join("|",pfs.stream().map(CategorySort::getDbWord).toList()));
        }

        if(!grs.isEmpty()){
            result.put("genre",String.join("|",grs.stream().map(CategorySort::getDbWord).toList()));
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
