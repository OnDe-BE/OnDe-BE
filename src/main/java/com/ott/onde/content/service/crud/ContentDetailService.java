package com.ott.onde.content.service.crud;

import com.ott.onde.content.dto.request.ContentRequest;
import com.ott.onde.content.dto.request.FilterRequest;
import com.ott.onde.content.dto.response.ContentResponse;
import com.ott.onde.content.repository.ContentRepository;
import com.ott.onde.content.service.ContentsServiceMethod;
import com.ott.onde.content.service.crud.crudImpl.ContentDetailServiceImpl;
import com.ott.onde.content.service.util.FilterMethod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class ContentDetailService implements ContentDetailServiceImpl {
    private final ContentRepository contentRepository;
    private final ContentsServiceMethod contentsServiceMethod;
    private final FilterMethod filterMethod;

    @Override
    public Page<ContentRequest> findSearchContentsByMultiCategory(String orderCategory, String category, int nowPage, int pageCount){
        PageRequest pageRequest = this.contentsServiceMethod.pagingRequestMethod(orderCategory,nowPage,pageCount);

        category = String.join("|",category.split("[ |,]"));

        Map<String,String> sr = this.contentsServiceMethod.sentenceSorting(category);

        Page<ContentResponse> paging;

        paging = sr.containsKey("platform") ? this.contentRepository.findContentByPlatformsAndContentId(pageRequest, sr.get("platform"),this.contentRepository.findContentsByCategory(sr.get("genre")).stream().map(ContentResponse::getContent_id).toList()) : this.contentRepository.findContentsByCategory(pageRequest,sr.get("genre"));

        return this.contentsServiceMethod.pageResponseToRequest(paging,pageCount * nowPage + 1);
    }

//    @Override
//    public Page<ContentResponse> findContentsBySentence(String orderCategory, String sentence, int nowPage, int pageCount){
//        PageRequest pageRequest = this.contentsServiceMethod.pagingRequestMethod(orderCategory,nowPage,pageCount);
//
////        return ;
//        sentence = this.contentsServiceMethod.sentenceSortingMethod(sentence);
//
//        Map<String, String> cates = this.contentsServiceMethod.sentenceSorting(sentence);
//
//        if(cates.size() >1){
//            return this.contentRepository.findContentsByPlatformAndCategory(pageRequest, cates.get("platform"),cates.get("genre"));
//        }else{
//            return this.contentSimpleService.findContentsByGenre(pageRequest,sentence);
//        }
//    }

    @Override
    public Page<ContentRequest> findFilteredContents(FilterRequest filterRequest, int nowPage, int pageCount){
        Page<ContentResponse> page = this.filterMethod.filteredContentLogic(filterRequest, nowPage, pageCount);

        return this.contentsServiceMethod.pageResponseToRequest(page, pageCount * nowPage + 1);
    }
}

// 두근 -> 심장소리 -> 공포/스릴러/반전 두근두근 -> 심장소리 -> 공포/로맨스/반전
// 뜨겁다 -> 가슴이 뛰는 -> 액션/학원/청춘/로
