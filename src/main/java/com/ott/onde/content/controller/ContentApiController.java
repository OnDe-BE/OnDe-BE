package com.ott.onde.content.controller;

import com.ott.onde.content.dto.request.ContentRequest;
import com.ott.onde.content.dto.response.ContentResponse;
import com.ott.onde.content.dto.ContentResult;
import com.ott.onde.content.dto.response.PlatformResponse;
import com.ott.onde.content.service.ContentCRUDService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/contents")
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class ContentApiController {
    private final ContentCRUDService contentCRUDService;

//    컨텐츠 상세 조회
    @GetMapping("/content")
    public ResponseEntity<Object> findContentByContentId(String contentId){
        ContentResult cl = this.contentCRUDService.findInformationOfContent(contentId);

        return ResponseEntity.ok().body(cl);
    }
    
//    컨텐츠 플랫폼 리스트 조회
    @GetMapping("/content/ott")
    public ResponseEntity<Object> findOttByContentId(String contentId){
        List< PlatformResponse> cl = this.contentCRUDService.findPlatformByContentId(contentId);

        return ResponseEntity.ok().body(cl);
    }

//    컨텐츠에 대한 검색
    @GetMapping("/search")
    public ResponseEntity<Object> findContentsByTitle(String search){
        List<ContentResponse> cl = this.contentCRUDService.findContentByTitle(search);

        return ResponseEntity.ok().body(cl);
    }

//    컨텐츠 필터별 조회
    @GetMapping("/content/category")
    public ResponseEntity<Object> findContentsByCategory(@Param("order") String order,
                                                            @Param("category") String category,
                                                            @Param("nowPage") int nowPage,
                                                            @Param("pageCount") int pageCount){
        long bfTime = System.currentTimeMillis();
        Page<ContentRequest> cl = this.contentCRUDService.findContentsByMultiCategory(order, category, nowPage, pageCount);
        long afTime = System.currentTimeMillis();

        log.info("building DB time : {}, bfTime : {}, afTime : {}, original Time : {}", (afTime - bfTime)/1000, bfTime, afTime, (afTime - bfTime));

        return ResponseEntity.ok().body(cl);
    }

//    컨텐츠 제목에 대한 검색
    @GetMapping("/content/ranking/category")
    public ResponseEntity<Object> findContentsByRanking(@Param("category") String category,
                                                            @Param("nowPage") int nowPage){
        Page<ContentRequest> cl = this.contentCRUDService.findContentsByMultiCategory("rank", category, nowPage, 20);

        return ResponseEntity.ok().body(cl);
    }

//  컨텐츠 문장/필터에 대한 검색
    @GetMapping("/content/sentence")
    public ResponseEntity<Object> findContentsBySentence(@Param("sentence") String sentence){
        long bfTime = System.currentTimeMillis();
        Page<ContentResponse> res = this.contentCRUDService.findContentsBySentence(sentence);
        long afTime = System.currentTimeMillis();

        log.info("building DB time : {}, bfTime : {}, afTime : {}, original Time : {}", (afTime - bfTime)/1000, bfTime, afTime, (afTime - bfTime));

        return ResponseEntity.ok().body(res);
    }

//    메인 배너 오늘의 추천 방식
    @PostMapping("/content/todayPick")
    public ResponseEntity<Object> findContentsByTodayPick(){
        long bfTime = System.currentTimeMillis();
        List<ContentResponse> res = this.contentCRUDService.findContentsByTodayPick();
        long afTime = System.currentTimeMillis();

        log.info("building DB time : {}, bfTime : {}, afTime : {}, original Time : {}", (afTime - bfTime)/1000, bfTime, afTime, (afTime - bfTime));

        return ResponseEntity.ok().body(res);
    }
}
