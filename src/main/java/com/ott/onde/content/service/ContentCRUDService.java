package com.ott.onde.content.service;

import com.ott.onde.content.dto.request.ContentRequest;
import com.ott.onde.content.dto.response.ContentListResponse;
import com.ott.onde.content.dto.response.ContentResponse;
import com.ott.onde.content.dto.ContentResult;
import com.ott.onde.content.dto.response.PlatformResponse;
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
import java.util.concurrent.atomic.AtomicInteger;

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

    public Page<ContentRequest> findContentsByMultiCategory(String orderCategory, String category, int nowPage, int pageCount){
        Map<String,String> sr = this.contentsServiceMethod.sentenceSorting(category);

        PageRequest pageRequest = this.contentsServiceMethod.pagingRequestMethod(orderCategory,nowPage,pageCount);

        AtomicInteger rank = new AtomicInteger(pageCount * nowPage + 1);

        if(sr.containsKey("platform")){
            return this.contentRepository.findContentsByPlatformAndCategory(pageRequest, sr.get("platform"), sr.get("genre")).map(x-> {
                return ContentRequest.builder().contentId(x.getContent_id())
                        .title(x.getTitle())
                        .age(x.getAge())
                        .contentImg(x.getContent_img())
                        .rank(rank.getAndIncrement()).build();
            });
        }

        return this.contentRepository.findContentsByCategory(pageRequest, sr.get("genre")).map(x-> {
            return ContentRequest.builder().contentId(x.getContent_id())
                    .title(x.getTitle())
                    .age(x.getAge())
                    .contentImg(x.getContent_img())
                    .rank(rank.getAndIncrement()).build();
        });
    }

//    문장에 대한 컨텐츠 추천 형식 -> 검색에서도 가능 하도록 구현한 형태
    public Page<ContentResponse> findContentsBySentence(String sentence){
        Map<String, String> cates = this.contentsServiceMethod.sentenceSorting(sentence);

        PageRequest pageRequest = this.contentsServiceMethod.pagingRequestMethod("rank",0,50);

        sentence = this.contentsServiceMethod.sentenceSortingMethod(sentence);

        if(cates.size() >1){
            return this.contentRepository.findContentsByPlatformAndCategory(pageRequest, cates.get("platform"),cates.get("genre"));
        }else{
            return this.contentRepository.findContentsByTitleAndSummary(pageRequest, sentence);
        }
    }

    public List<ContentResponse> findContentsByTodayPick(){
        return this.contentRepository.findContentsByTodayPick();
    }
}
