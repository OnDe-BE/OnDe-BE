package com.ott.onde.content.controller;


import com.ott.onde.content.dto.request.ContentRequest;
import com.ott.onde.content.dto.request.FilterRequest;
import com.ott.onde.content.dto.response.PlatformResponse;
import com.ott.onde.content.dto.result.ContentDetailResult;
import com.ott.onde.content.service.crud.ContentDetailService;
import com.ott.onde.content.service.crud.ContentSimpleService;
import com.ott.onde.content.service.util.UserPreferContent;
import com.ott.onde.user.service.UserService;
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
    private final ContentSimpleService contentSimpleService;
    private final UserPreferContent userPreferContent;
    private final UserService userService;
    private final ContentDetailService contentDetailService;

//    컨텐츠 상세 조회
    @GetMapping("/content")
    public ResponseEntity<Object> findContentByContentId(@Param("contentId") String contentId){
        ContentDetailResult cl = this.contentSimpleService.findContentDetails(contentId);

        return ResponseEntity.ok().body(cl);
    }
    
//    컨텐츠 플랫폼 리스트 조회
    @PostMapping("/ott")
    public ResponseEntity<Object> findOttByContentId(@Param("contentId") String contentId){
        List< PlatformResponse> cl = this.contentSimpleService.findPlatformByContentId(contentId);

        return ResponseEntity.ok().body(cl);
    }

//    컨텐츠에 대한 검색
    @PostMapping("/search")
    public ResponseEntity<Object> findContentsByTitle(@Param("search") String search){
        Page<ContentRequest> cl = this.contentSimpleService.findContentByTitle(search);

        return ResponseEntity.ok().body(cl);
    }

//    콘텐츠 검색에 대한 문장/장르에 대한 조회
    @PostMapping("/category")
    public ResponseEntity<Object> findContentsByCategory(@RequestParam(name = "order", required = false, defaultValue = "인기순") String order,
                                                            @RequestParam(name = "category") List<String> category,
                                                            @Param("nowPage") int nowPage,
                                                            @RequestParam(name = "pageCount", required = false, defaultValue = "50") int pageCount){
        long bfTime = System.currentTimeMillis();
        Page<ContentRequest> cl = this.contentDetailService.findSearchContentsByMultiCategory(order, String.join(" ",category), nowPage, pageCount);
        long afTime = System.currentTimeMillis();

        log.info("building DB time : {}, bfTime : {}, afTime : {}, original Time : {}", (afTime - bfTime)/1000, bfTime, afTime, (afTime - bfTime));

        return ResponseEntity.ok().body(cl);
    }

//    컨텐츠 제목에 대한 검색
    @PostMapping("/ranking/category")
    public ResponseEntity<Object> findContentsByRanking(@Param("category") String category,
                                                            @Param("nowPage") int nowPage){
        Page<ContentRequest> cl = this.contentDetailService.findSearchContentsByMultiCategory("rank", category, nowPage, 20);

        return ResponseEntity.ok().body(cl);
    }

//  컨텐츠 문장/필터에 대한 검색
    @PostMapping("/sentence")
    public ResponseEntity<Object> findContentsBySentence(@RequestParam(name = "order", required = false, defaultValue = "인기순") String order,
                                                         @Param("sentence") String sentence,
                                                         @Param("nowPage") int nowPage,
                                                         @RequestParam(name = "pageCount", required = false, defaultValue = "50") int pageCount){
        Page<ContentRequest> res = this.contentDetailService.findSearchContentsByMultiCategory(order,sentence,nowPage,pageCount);

        return ResponseEntity.ok().body(res);
    }

//    메인 배너 오늘의 추천 방식
    @PostMapping("/todayPick")
    public ResponseEntity<Object> findContentsByTodayPick(){
        Page<ContentRequest> res = this.contentSimpleService.findContentsByTodayPick();

        return ResponseEntity.ok().body(res);
    }

    // @PostMapping("/recommend")
    // public ResponseEntity<Object> findContentsByRecommend(@AuthenticationPrincipal User user){
        // user = this.userService.findUser("20250220");
//            List<String> prefers = this.userPreferContent.genreSorting(userPreferContent.preferSentenceSorting(user),userPreferContent.preferGenreSorting(user));
    //     Page<ContentRequest> prefers = this.userPreferContent.recommendedContent(user);

    //     return ResponseEntity.ok().body(prefers);
    // }

    @PostMapping("/filter")
    public ResponseEntity<Object> findContentsByFilter(@RequestParam(value = "age", required = false) List<String> age,
                                                       @RequestParam(value = "released", required = false) List<Integer> released,
                                                       @RequestParam(value = "genre", required = false) List<String> genre,
                                                       @RequestParam(value = "userAge", required = false) List<Integer> userAge,
                                                       @RequestParam(value = "gender", required = false) String gender,
                                                       @RequestParam(value = "cType", required = false) List<String> cType,
                                                       @RequestParam(value = "nowPage", required = false) int nowPage,
                                                       @RequestParam(value = "pageCount", required = false, defaultValue = "50") int pageCount){
        FilterRequest filterRequest = FilterRequest.builder()
                .age(age)
                .released(released)
                .userAge(userAge)
                .genre(genre)
                .gender(gender)
                .cType(cType).build();
        Page<ContentRequest> contents = this.contentDetailService.findFilteredContents(filterRequest, nowPage, pageCount);

        return ResponseEntity.ok().body(contents);
    }
}
