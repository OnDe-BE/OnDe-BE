package com.ott.onde.content.service.util.serivce;

import java.util.List;
import java.util.Map;

public interface FilterWordData {
    public String getFilterWord(String sentence);
    public Map<String,String> compareSentenceWithDB(String sentence);
    public Map<String, Integer> sentenceWordPreferCount(List<String> sentence);

    public void filteringMethod(String sentence);
}
