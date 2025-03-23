package com.ott.onde.content.service.util.serivce;

import com.ott.onde.content.dto.request.FilterRequest;
import com.ott.onde.content.dto.response.ContentResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FilterCase {
    public Page<ContentResponse> filteredContentLogic(FilterRequest request,int page, int size);
    public FilterRequest filterParamDefaultValueSet(FilterRequest request);
    public FilterRequest filterParamSingleValueSet(FilterRequest request);
    public List<String> filterUserParam(FilterRequest request);
}
