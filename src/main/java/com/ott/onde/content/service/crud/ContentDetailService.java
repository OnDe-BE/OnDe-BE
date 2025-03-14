package com.ott.onde.content.service.crud;

import com.ott.onde.content.dto.request.ContentRequest;
import com.ott.onde.content.dto.request.FilterRequest;
import org.springframework.data.domain.Page;

public interface ContentDetailService {
//  장르에 따른 조회
    public Page<ContentRequest> findSearchContentsByMultiCategory(String orderCategory, String category, int nowPage, int pageCount);
//    장르에 따른 조회
//    public Page<ContentRequest> findTest(String orderCategory, String category, int nowPage, int pageCount);
//  문장에 따른 조회
//    public Page<ContentResponse> findContentsBySentence(String orderCategory, String sentence, int nowPage, int pageCount);
//  필터에 따른 조회
    public Page<ContentRequest> findFilteredContents(FilterRequest filterRequest,int nowPage, int pageCount);
}
