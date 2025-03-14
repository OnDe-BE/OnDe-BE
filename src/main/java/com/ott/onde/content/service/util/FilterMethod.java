package com.ott.onde.content.service.util;

import com.ott.onde.content.dto.request.FilterRequest;
import com.ott.onde.content.dto.response.ContentIdResponse;
import com.ott.onde.content.dto.response.ContentResponse;
import com.ott.onde.content.repository.ContentRepository;
import com.ott.onde.content.service.ContentsServiceMethod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class FilterMethod implements FilterCase {
    private final ContentRepository contentRepository;
    private final ContentsServiceMethod contentsServiceMethod;

    @Override
    public Page<ContentResponse> filteredContentLogic(FilterRequest request, int nowPage, int pageSize) {
        request = this.filterParamSingleValueSet(this.filterParamDefaultValueSet(request));

//        Map<String, Boolean> type = new HashMap<>();
        StringBuilder genres = new StringBuilder(request.getGenre());

        for (String str : request.getCType()){
            if(!str.equals("영화")){
                genres.append("|").append(str);
            }
        }

        List<String> contentId = this.contentRepository.findContentIdByCategory(genres.toString()).stream().map(ContentIdResponse::getContentId).toList();

        PageRequest pageRequest = this.contentsServiceMethod.pagingRequestMethod("rank", nowPage, pageSize);

        return request.getCType().contains("영화") ? this.contentRepository.findContentsByContentIdAndAgeAndReleasedAndCType(pageRequest, contentId, String.join("|",request.getAge()), request.getReleased().get(0),request.getReleased().get(1), "movie") : this.contentRepository.findContentsByContentIdAndAgeAndReleasedAndCType(pageRequest, contentId, String.join("|",request.getAge()), request.getReleased().get(0),request.getReleased().get(1), "movie|series");
    }

    @Override
    public FilterRequest filterParamDefaultValueSet(FilterRequest request) {
        if(request.getUserAge() == null) request.setUserAge(List.of(10, 100));

        return request;
    }

    @Override
    public FilterRequest filterParamSingleValueSet(FilterRequest request) {
        if(request.getReleased().size() == 1){
            request.setReleased(List.of(request.getReleased().get(0), request.getReleased().get(0)+9));
        }
        if(request.getUserAge().size() == 1){
            request.setUserAge(List.of(request.getUserAge().get(0), request.getUserAge().get(0) + 9));
        }

        return request;
    }

    @Override
    public List<String> filterUserParam(FilterRequest request) {
        
        return List.of();
    }
}
