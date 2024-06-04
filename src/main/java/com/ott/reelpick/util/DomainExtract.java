package com.ott.reelpick.util;

public class DomainExtract {
    public static String extractDomain(String email) {
        int atIndex = email.indexOf("@");
        int dotIndex = email.lastIndexOf(".com"); // ".com"의 인덱스 찾기
        if (atIndex != -1 && dotIndex != -1) {
            return email.substring(atIndex + 1, dotIndex); // "@" 다음부터 ".com" 앞까지
        } else {
            return null; // 이메일 형식이 아닌 경우
        }
    }
}
