package com.ott.onde.content.service.serviceImpl;

import com.ott.onde.content.dto.request.ContentRequest;
import com.ott.onde.content.dto.request.FilterRequest;
import com.ott.onde.content.dto.response.ContentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Map;

public interface ContentDetailServiceImpl {
//  장르에 따른 조회
    public Page<ContentRequest> findSearchContentsByMultiCategory(String orderCategory, String category, int nowPage, int pageCount);
//    장르에 따른 조회
//    public Page<ContentRequest> findTest(String orderCategory, String category, int nowPage, int pageCount);
//  문장에 따른 조회
//    public Page<ContentResponse> findContentsBySentence(String orderCategory, String sentence, int nowPage, int pageCount);
//  필터에 따른 조회
    public Page<ContentRequest> findFilteredContents(FilterRequest filterRequest, int nowPage, int pageCount);
}
