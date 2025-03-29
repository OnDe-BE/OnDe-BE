package com.ott.onde.content.service.util.method;

import com.ott.onde.content.dto.request.ContentRequest;
import com.ott.onde.content.dto.request.GenreRequest;
import com.ott.onde.content.dto.response.ContentResponse;
import com.ott.onde.content.entity.util.CategorySort;
import com.ott.onde.content.repository.util.CategorySortRepository;
import com.ott.onde.content.repository.detail.ContentMovieRepository;
import com.ott.onde.content.repository.genre.ContentGenreRepository;
import com.ott.onde.content.repository.detail.series.InnerSeriesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j@Service@RequiredArgsConstructor
public class ContentsServiceMethod {
    private final CategorySortRepository cateSortRepository;
    private final ContentGenreRepository contentGenreRepository;
    private final ContentMovieRepository contentMovieRepository;
    private final InnerSeriesRepository seriesRepository;

    public Map<String, String> sentenceSorting(String sentence){
        Map<String, String> result = new HashMap<>();

        for(CategorySort c : this.sentenceDivideType(sentence)){
            result = this.sentenceToWord(c, result);
        }

        return result;
    }

    public List<CategorySort> sentenceDivideType(String sentence){
        return new ArrayList<>(this.cateSortRepository.findByWord(String.join("|", sentence.split(" "))));
    }

    public Map<String, String> sentenceToWord(CategorySort c,Map<String, String> result){
        result.put(c.getCategory(), result.containsKey(c.getCategory()) ? result.get(c.getCategory()) + "|" + c.getDbWord() : c.getDbWord());

        return result;
    }

    public PageRequest pagingRequestMethod(String orderCategory, int nowPage, int pageCount){
        orderCategory = orderCategory == null ? "인기순" : orderCategory;

        Sort sort = switch (orderCategory) {
            case "인기순" -> Sort.by(Sort.Direction.DESC, "hit_point");
            case "최신순" -> Sort.by(Sort.Direction.DESC, "released");
            default -> Sort.by(Sort.Direction.DESC, "title");
        };

        return PageRequest.of(nowPage, pageCount, sort);
    }

    public List<String> findGenresByContentId(String contentId){
        return this.contentGenreRepository.findGenreByContentId(contentId);
    }

    public Map<String, List<String>> findGenresByContentId(List<String> contentId){
        List<GenreRequest> genres = this.contentGenreRepository.findGenreByContentId(contentId);

        Map<String, List<String>> result = new HashMap<>();
        genres.stream().map(GenreRequest::getContent_id).forEach(content -> {
            List<String> genre = genres.stream().filter(x->x.getContent_id().equals(content)).map(GenreRequest::getGenre).toList();
            result.put(content, genre);
        });

        return result;
    }

    public Page<ContentRequest> pageResponseToRequest(Page<ContentResponse> paging, int number){
        AtomicInteger rank = new AtomicInteger(number);

        List<String> contentIds = paging.stream().map(ContentResponse::getContent_id).toList();

         Map<String, List<String>> genres = this.findGenresByContentId(contentIds);

        return paging.map(x-> ContentRequest.builder().contentId(x.getContent_id())
                .title(x.getTitle())
                .age(x.getAge())
                .contentImg(x.getContent_img())
                .rank(rank.getAndIncrement())
                .genres(genres.get(x.getContent_id())).build());
    }

    public List<ContentRequest> responseToRequest(List<ContentResponse> paging){
        AtomicInteger rank = new AtomicInteger(1);

        List<String> contentIds = paging.stream().map(ContentResponse::getContent_id).toList();

        Map<String, List<String>> genres = this.findGenresByContentId(contentIds);

        return paging.stream().map(x-> ContentRequest.builder().contentId(x.getContent_id())
                .title(x.getTitle())
                .age(x.getAge())
                .contentImg(x.getContent_img())
                .rank(rank.getAndIncrement())
                .genres(genres.get(x.getContent_id())).build()).toList();
    }

    public String runtimeByClassifyType(String contentId, String cType){
        return cType.equals("movie") ? this.contentMovieRepository.findRuntimeByContentId(contentId) : this.seriesRepository.findRuntimeByContentId(contentId);
    }
}
