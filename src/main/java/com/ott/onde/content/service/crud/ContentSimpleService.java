package com.ott.onde.content.service.crud;

import com.ott.onde.content.dto.ContentResult;
import com.ott.onde.content.dto.request.ContentRequest;
import com.ott.onde.content.dto.response.ContentListResponse;
import com.ott.onde.content.dto.response.ContentResponse;
import com.ott.onde.content.dto.response.PlatformResponse;
import com.ott.onde.content.repository.ContentPlatformRepository;
import com.ott.onde.content.repository.ContentRepository;
import com.ott.onde.content.repository.ContentViewRepository;
import com.ott.onde.content.service.ContentsServiceMethod;
import com.ott.onde.content.service.crud.crudImpl.ContentSimpleServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor@Service@Slf4j
public class ContentSimpleService implements ContentSimpleServiceImpl {
    private final ContentRepository contentRepository;
    private final ContentPlatformRepository contentPlatformRepository;
    private final ContentViewRepository contentViewRepository;
    private final ContentsServiceMethod contentsServiceMethod;

    @Override
    public Page<ContentRequest> findContentByTitle(String contentTitle){
        String titleExp = contentTitle.replace(" ", "|");
        PageRequest pageRequest = PageRequest.of(0, 20);

        Optional<Page<ContentResponse>> contents = this.contentRepository.findByTitle(pageRequest, titleExp);

        return contents.isPresent() ? this.contentsServiceMethod.pageResponseToRequest(contents.get(),0) : this.findContentsByTodayPick();
    }

    @Override
    public ContentResult findContentDetails(String contentId){
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

    @Override
    public List<PlatformResponse> findPlatformByContentId(String contentId){
        return this.contentPlatformRepository.findPlatformByContentId(contentId);
    }

    @Override
    public Page<ContentResponse> findContentsByGenre(PageRequest pageRequest, String genre){
        return this.contentRepository.findContentsByTitleAndSummary(pageRequest, genre);
    }

    @Override
    public Page<ContentRequest> findContentsByTodayPick(){
        Page<ContentResponse> cr = this.contentRepository.findContentsByTodayPick(this.contentsServiceMethod.pagingRequestMethod("rank",0,20));

        return this.contentsServiceMethod.pageResponseToRequest(cr,0);
    }

    @Override
    public Page<ContentResponse> findContentsByAge(PageRequest pageRequest, List<Integer> age){
        String ageExp = String.join("|",age.toString());

        return this.contentRepository.findContentByAge(ageExp, pageRequest);
    }

    @Override
    public Page<ContentResponse> findContentsByReleased(PageRequest pageRequest, List<Integer> released){
        return this.contentRepository.findContentByReleased(released.get(0), released.get(1), pageRequest);
    }

    @Override
    public Page<ContentRequest> findContentsByGender(String gender){
        return null;
    }

    public Page<ContentRequest> findContentsByUserAge(int age){
        return  null;
    }
}
