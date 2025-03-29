package com.ott.onde.content.service.contents.serviceImpl;

import com.ott.onde.content.dto.request.ContentRequest;
import com.ott.onde.content.dto.request.FilterRequest;
import com.ott.onde.content.dto.response.ContentResponse;
import com.ott.onde.content.repository.ContentRepository;
import com.ott.onde.content.service.contents.service.ContentDetailService;
import com.ott.onde.content.service.util.method.ContentsServiceMethod;
import com.ott.onde.content.service.util.method.FilterMethod;
import com.ott.onde.content.service.util.method.FilterWordDataMethod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class ContentDetailServiceImpl implements ContentDetailService {
    private final ContentRepository contentRepository;
    private final ContentsServiceMethod contentsServiceMethod;
    private final FilterMethod filterMethod;
    private final FilterWordDataMethod filterWordDataMethod;


    @Override
    public Page<ContentRequest> findSearchContentsByMultiCategory(String orderCategory, String category, int nowPage, int pageCount){
        PageRequest pageRequest = this.contentsServiceMethod.pagingRequestMethod(orderCategory,nowPage,pageCount);

        if(category == null || category.isEmpty()){
            return this.contentsServiceMethod.pageResponseToRequest(this.contentRepository.findContentsAll(pageRequest),pageCount * nowPage + 1);
        }

        category = String.join("|",category.split("[ |,]"));

        Map<String,String> sr = this.contentsServiceMethod.sentenceSorting(category);

        return this.contentsServiceMethod.pageResponseToRequest(this.findContentsByCategory(pageRequest,sr),pageCount * nowPage + 1);
    }

    @Override
    public Page<ContentRequest> findFilteredContents(FilterRequest filterRequest, int nowPage, int pageCount){
        Page<ContentResponse> page = this.filterMethod.filteredContentLogic(filterRequest, nowPage, pageCount);

        return this.contentsServiceMethod.pageResponseToRequest(page, nowPage * pageCount + 1);
    }

    public Page<ContentRequest> findSentenceContents(String sentence){
        Map<String,String> sr = this.filterWordDataMethod.compareSentenceWithDB(sentence);

        return this.contentsServiceMethod.pageResponseToRequest(this.findContentsByCategory(PageRequest.of(0,20),sr),1);
    }

    public Page<ContentResponse> findContentsByCategory(PageRequest pageRequest, Map<String,String> sr){
        if(sr.containsKey("platform")){
            return sr.containsKey("genre") ? this.contentRepository.findContentByPlatformsAndContentId(pageRequest, sr.get("platform"),this.contentRepository.findContentsByCategory(sr.get("genre")).stream().map(ContentResponse::getContent_id).toList()) :
                    this.contentRepository.findContentsByPlatform(pageRequest,sr.get("platform"));
        }else{
            return this.contentRepository.findContentsByCategory(pageRequest,sr.get("genre"));
        }
    }
}
