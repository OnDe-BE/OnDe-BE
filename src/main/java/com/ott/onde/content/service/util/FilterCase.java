package com.ott.onde.content.service.util;

import com.ott.onde.content.dto.request.FilterRequest;
import com.ott.onde.content.dto.response.ContentResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FilterCase {
    //  1. 장르 없을 시 장르에서 가져오는 contentId 로직 없이 진행 -> return 되는 부분 도 변경해야함
//        2. released, userAge 대한 부분들 1개의 값일 시 regexp
//         -> 하나만 선택시 released = start, end 값을 동일시 혹은 end = start + 9의 형태로 10단위로 범위 값 생성
//         userAge 부분은 현재 10,20... 등의 형태를 지니고 있어 end = start + 9
//        3. gender 부분 null일 시 ->
//        4. cType 장르, type 구분 -> 영화는 cType으로 나머지 시리즈일 가능성이 많음, null일 시 cType = "movie|series"
//        5. age,(released),(userAge) default value -> 쿼리에서 삭제 || 초기값 설정 -> 초기값 설정 시에는 regExp 혹은 between으로 설정 ->
//        age값은 현재 varchar형태의 값 -> int값으로 변경 시 between사용 가능 0,7,12,15,19의 형태로 변경 -> 비교 부분이 많아짐 현재 상태 유지
//
//        현재 파라미터 제외 값 -> userAge, gender
//        차 후 추가 테이블 작업 이후 해당 테이블에서 해당 컬럼들 비교하는 로직 생성 필요

    public Page<ContentResponse> filteredContentLogic(FilterRequest request,int page, int size);
    public FilterRequest filterParamDefaultValueSet(FilterRequest request);
    public FilterRequest filterParamSingleValueSet(FilterRequest request);
    public List<String> filterUserParam(FilterRequest request);
}
