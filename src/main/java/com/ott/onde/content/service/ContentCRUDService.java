package com.ott.onde.content.service;


import com.ott.onde.content.dto.ContentListResponse;
import com.ott.onde.content.dto.ContentResponse;
import com.ott.onde.content.dto.ContentResult;
import com.ott.onde.content.dto.PlatformResponse;
import com.ott.onde.content.entity.Content;
import com.ott.onde.content.repository.ContentPlatformRepository;
import com.ott.onde.content.repository.ContentRepository;
import com.ott.onde.content.repository.ContentViewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor@Service@Slf4j
public class ContentCRUDService {
    private final ContentRepository contentRepository;
    private final ContentPlatformRepository contentPlatformRepository;
    private final ContentViewRepository contentViewRepository;
    private final ContentsServiceMethod contentsServiceMethod;

    public Content findByContent(String contentId){
        return this.contentRepository.findByContentId(contentId);
    }

    public List<ContentResponse> findContentByTitle(String contentTitle){
        String titleExp = contentTitle.replace(" ", "|");

        Optional<List<ContentResponse>> contents = this.contentRepository.findByTitle(titleExp);

        return contents.orElse(null);
    }

    public ContentResult findInformationOfContent(String contentId){
        Optional<List<ContentListResponse>> res = this.contentRepository.findContentsByContentId(contentId);

        if(res.isPresent()){
            ContentListResponse rs = res.get().get(0);
            List<String> genres = res.get().stream().map(ContentListResponse::getGenre).toList();

            this.contentViewRepository.updateHitPointContent(rs.getContent_id());

            return ContentResult.builder()
                    .contentId(rs.getContent_id())
                    .title(rs.getTitle())
                    .summary(rs.getSummary())
                    .cType(rs.getC_type())
                    .age(rs.getAge())
                    .released(rs.getReleased())
                    .contentImg(rs.getContent_img())
                    .genre(genres).build();
        }else{
            return ContentResult.builder().build();
        }
    }

    public List<PlatformResponse> findPlatformByContentId(String contentId){
        return this.contentPlatformRepository.findPlatformByContentId(contentId);
    }

    public Page<ContentResponse> findContentsByMultiCategory(String orderCategory, String category, int nowPage, int pageCount){
//      !현재 카테고리 판별 방식은 or로만 작동하는 형태 즉 genre and를 통한 데이터 우선 추출 logic이 none
//        해당 logic으로 변경해야함
        Map<String,String> sr = this.contentsServiceMethod.sentenceSorting(category);

        PageRequest pageRequest = this.contentsServiceMethod.pagingRequestMethod(orderCategory,nowPage,pageCount);

        if(sr.containsKey("platform")){
            return this.contentRepository.findContentsByPlatformAndCategory(pageRequest, sr.get("platform"), sr.get("genre"));
        }

        return this.contentRepository.findContentsByCategory(pageRequest, sr.get("genre"));
    }

//    문장에 대한 컨텐츠 추천 형식 -> 검색에서도 가능 하도록 구현한 형태
    public Page<ContentResponse> findContentsBySentence(String sentence){
        //        1. 문장 변환 -> 단어 조각 -> 사용 가능 단어 판별
        Map<String, String> cates = this.contentsServiceMethod.sentenceSorting(sentence);
//        2-1. 결과 값으로 생성된 단어들의 존재 여부 판단
//
//        ! 변수 -> 단어들 1개라도 존재 시 해당 단어만을 위한 데이터라고 인식되어 해당 단어 외의 나머지 단어들은 더 이상 사용 가능 단어가 아니게되는 결과 값이 발생
//        ! DB내 존재하지않는 최초 변수 데이터들에 대한 분석 및 사용가능 데이터로 마이닝 하는 방법 develop의 필요성
//
//        2-2. 존재 여부에 따라 해당 단어 대분류로 나눠 분류
//
//        3-1. 데이터 exist
//        1. 기존의 카테고리 분류 방식과 동일 해당 keyword들을 통한 data 추출
//
//        3-2. 데이터 empty
//        1. 해당 단어들의 slicing keyword를 title, summary ... etc 대입을 통하여 임시 추천
//        이 후 해당 추천된 keyword관련 단어와 연관성이 있다고 판단되는 contents의 category를 분석하여 DB에 학습
//        -> 조회 logic에서 판별하는 형태를 가져갈 수 있도록 변경

        PageRequest pageRequest = this.contentsServiceMethod.pagingRequestMethod("rank",0,50);
        sentence = this.contentsServiceMethod.sentenceSortingMethod(sentence);

        log.info("sentence : {}", sentence);

        if(cates.size() >1){
            return this.contentRepository.findContentsByPlatformAndCategory(pageRequest, cates.get("platform"),cates.get("genre"));
        }else if(cates.isEmpty()){

            return this.contentRepository.findContentsByTitleAndSummary(pageRequest, sentence);

        }
        return this.findContentsByMultiCategory("rank", cates.get("genre"), 0, 50);
    }

    public List<ContentResponse> findContentsByTodayPick(){
        return this.contentRepository.findContentsByTodayPick();
    }
}
