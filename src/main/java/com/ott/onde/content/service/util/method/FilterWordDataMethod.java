package com.ott.onde.content.service.util.method;

import com.ott.onde.content.entity.util.CategorySort;
import com.ott.onde.content.repository.util.CategorySortRepository;
import com.ott.onde.content.service.util.serivce.FilterWordData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service@RequiredArgsConstructor@Slf4j
public class FilterWordDataMethod implements FilterWordData {
    private final CategorySortRepository categorySortRepository;

    @Override
    public String getFilterWord(String sentence) {
        return String.join("|",sentence.split(""));
    }

    @Override
    public Map<String,String> compareSentenceWithDB(String sentence) {
//        해당 자른문자열을 DB에서 단어와 비교 후 포함된 CategorySort데이터 추출
//        *********************** 가장 중요한 문장의 가장 중심되는 단어는 무엇인지 판별하는 코드가 필요 ***************************
        List<CategorySort> categorySorts = this.categorySortRepository.findByWord(this.getFilterWord(sentence)).stream().filter(x -> sentence.contains(x.getWord())).toList();
//        List<String> words = new ArrayList<>();
//
//        for(CategorySort cs : categorySorts) {
//            log.info("word: {}, dbWord : {}, divideWord : {}",cs.getWord(), cs.getDbWord(),this.wordDivide(cs.getWord(), sentence));
//            words.add(this.wordDivide(cs.getWord(), sentence));
//        }

//        return words;
        Map<String,String> map = new HashMap<>();
        categorySorts.forEach(cs -> {
            map.put(cs.getCategory(),map.containsKey(cs.getCategory()) ? map.get(cs.getCategory()) + "|"+cs.getDbWord() : cs.getDbWord());
        });

        return map;
    }

    public String wordDivide(String word, String sentence){
//        해당 단어 문장에 대해 포함여부 판단 후 해당 단어 리턴
        return sentence.substring(sentence.indexOf(word), sentence.indexOf(word.substring(word.length() - 1), sentence.indexOf(word))+1);
    }

    @Override
    public Map<String, Integer> sentenceWordPreferCount(List<String> sentence) {
        Map<String, Integer> map = new HashMap<>();
        for(String word : sentence) {
            map.put(word, map.getOrDefault(word,0)+1);
        }

        return map;
    }

    @Override
    public void filteringMethod(String sentence) {
//        List<String> words = this.compareSentenceWithDB(sentence);

//        Map<String, Integer> map = this.sentenceWordPreferCount(words);


    }
}
