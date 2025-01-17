package com.onde.contentsSample.service.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service@Slf4j
public class PlatformSortData {
    public String platformSorting(String platform){
        log.info(platform);
        return switch (platform) {
            case "넷플릭스","netflix","넷플" -> "netflix";
            case "티빙","tving" -> "tving";
            default -> "";
        };
    }
}
