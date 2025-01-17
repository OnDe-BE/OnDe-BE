package com.onde.contentsSample.service.util;

import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;

@Service
public class SentenceSortData {
    public MultiValueMap<String, String> sentences(String data){
        MultiValueMap<String, String> arr = new LinkedMultiValueMap<>();

        arr.add("액션", "액션");
        arr.add("화려한", "액션");
        arr.add("역동", "액션");
        arr.add("킬링", "액션");
        arr.add("심심", "액션");
        arr.add("로맨스", "로맨스");
        arr.add("연애", "로맨스");
        arr.add("편안", "로맨스");
        arr.add("가족", "가족");
        arr.add("감동", "실화");
        arr.add("청춘", "청춘");
        arr.add("청춘", "로맨스");
        arr.add("학창", "청춘");
        arr.add("학생", "청춘");
        arr.add("그 시절", "청춘");
        arr.add("추억", "청춘");
        arr.add("추억", "로맨스");
        arr.add("회상", "청춘");
        arr.add("빛나는", "어린 시절");
        arr.add("반전", "미스터리");
        arr.add("반전", "추리");
        arr.add("충격", "추리");
        arr.add("충격", "미스터리");
        arr.add("서스펜스", "미스터리");
        arr.add("싸늘", "공포");
        arr.add("싸늘", "미스터리");
        arr.add("함께", "가족");
        arr.add("함께", "로맨스");
        arr.add("두근", "로맨스");
        arr.add("두근", "공포");
        arr.add("따뜻", "로맨스");
        arr.add("따뜻", "가족");
        arr.add("주인공", "액션");
        arr.add("공감", "토크쇼");
        arr.add("힐링", "청춘");
        arr.add("힐링", "가족");
        arr.add("판타지", "판타지");
        arr.add("판타지", "회귀물");
        arr.add("판타지", "SF");
        arr.add("현실", "실화");
        arr.add("현실탈출", "SF");


        MultiValueMap<String, String> res = new LinkedMultiValueMap<>();

        for(String key : arr.keySet()){
            if(data.matches("(.*)"+key+"(.*)")){
                for(String val : Objects.requireNonNull(arr.get(key))){
                    res.add(key, val);
                }
            }
        }

        return res;
    }




//    문장으로 들어온다 해당 단어 포함 여부 확인
//    해당 단어 포함 여부에 대한 해당 단어 관련 장르 찾기
    
//    public String sentenceSorting(String sentence){
//
//        return Arrays.stream(arr).filter(x -> sentence.matches("(.*)"+x+"(.*)")).toString();
//    }
    
//    public String typeSorting
}
