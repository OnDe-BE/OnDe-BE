package com.ott.onde.util;

import java.util.Random;

public class RandomTag {
    public static String createHashtag() {
        Random random = new Random();
        int number = random.nextInt(10000); // 0에서 9999 사이의 숫자 생성
        return String.format("%04d", number); // 4자리 문자열로 포맷팅
    }
}
