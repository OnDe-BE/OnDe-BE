package com.ott.onde.content.service.crud.crudImpl;

import com.ott.onde.content.dto.ContentResult;
import com.ott.onde.content.dto.request.ContentRequest;
import com.ott.onde.content.dto.response.ContentResponse;
import com.ott.onde.content.dto.response.PlatformResponse;
import com.ott.onde.content.dto.result.ContentDetailResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ContentSimpleServiceImpl {
//    제목에 대한 조회
    public Page<ContentRequest> findContentByTitle(String contentTitle);
//    콘텐츠 상세정보 조회
    public ContentDetailResult findContentDetails(String contentId);
//    플랫폼 조회
    public List<PlatformResponse> findPlatformByContentId(String contentId);
//    장르에 따른 검색
    public Page<ContentResponse> findContentsByGenre(PageRequest pageRequest, String genre);
//    오늘의 추천
    public Page<ContentRequest> findContentsByTodayPick();
//    해당 사용자연령들의 조회수에 따른 조회
    public Page<ContentResponse> findContentsByAge(PageRequest pageRequest, List<Integer> age);
//    년도에 따른 조회
    public Page<ContentResponse> findContentsByReleased(PageRequest pageRequest, List<Integer> released);
//    사용자성별의 조회수에 따른 조회
    public Page<ContentRequest> findContentsByGender(String gender);
//    등급에 따른 조회
    public Page<ContentRequest> findContentsByUserAge(int age);
}
