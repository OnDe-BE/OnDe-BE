package com.onde.contentsSample.service;

import com.onde.contentsSample.dto.ContentListResponse;
import com.onde.contentsSample.dto.ContentResponse;
import com.onde.contentsSample.dto.ContentResult;
import com.onde.contentsSample.dto.PlatformResponse;
import com.onde.contentsSample.dto.util.SearchResponse;
import com.onde.contentsSample.entity.Content;
import com.onde.contentsSample.repository.ContentPlatformRepository;
import com.onde.contentsSample.repository.ContentRepository;
import com.onde.contentsSample.repository.ContentViewRepository;
import com.onde.contentsSample.repository.InnerGenreRepository;
import io.micrometer.core.instrument.search.Search;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor@Service@Slf4j
public class ContentCRUDService {
    private final ContentRepository contentRepository;
    private final ContentPlatformRepository contentPlatformRepository;
    private final ContentViewRepository contentViewRepository;
    private final SentenceSorting sentenceSorting;
    private final CategorySort categorySort;

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
        Map<String,String> sr = this.sentenceSorting.sentenceSorting(category);

        Sort sort = switch (orderCategory) {
            case "rank" -> Sort.by(Sort.Direction.DESC, "hit_point");
            case "new" -> Sort.by(Sort.Direction.DESC, "released");
            default -> Sort.by(Sort.Direction.DESC, "title");
        };

        PageRequest pageRequest = PageRequest.of(nowPage, pageCount, sort);

        if(sr.containsKey("platform")){
            return this.contentRepository.findContentsByPlatformAndCategory(pageRequest, sr.get("platform"), sr.get("genre"));
        }

        return this.contentRepository.findContentsByCategory(pageRequest, sr.get("genre"));
    }

//    문장에 대한 컨텐츠 추천 형식 -> 검색에서도 가능 하도록 구현한 형태
    public Page<ContentResponse> findContentsBySentence(String sentence){
        return this.findContentsByMultiCategory("rank", sentence, 0, 50);
    }

    public List<ContentResponse> findContentsByTodayPick(){

        return this.contentRepository.findContentsByTodayPick();
    }
}
