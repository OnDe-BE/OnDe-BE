package com.ott.onde.content.service.contents.serviceImpl;

import com.ott.onde.content.dto.request.ContentRequest;
import com.ott.onde.content.dto.response.ContentDetailResponse;
import com.ott.onde.content.dto.response.ContentResponse;
import com.ott.onde.content.dto.response.PlatformResponse;
import com.ott.onde.content.dto.result.ContentDetailResult;
import com.ott.onde.content.repository.detail.ContentPlatformRepository;
import com.ott.onde.content.repository.ContentRepository;
import com.ott.onde.content.repository.user.ContentViewRepository;
import com.ott.onde.content.service.contents.service.ContentSimpleService;
import com.ott.onde.content.service.util.method.ContentsServiceMethod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor@Service@Slf4j
public class ContentSimpleServiceImpl implements ContentSimpleService {
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
    public ContentDetailResult findContentDetails(String contentId){
        Optional<ContentDetailResponse> res = this.contentRepository.findContentsByContentId(contentId);

        if(res.isPresent()){
            ContentDetailResponse rs = res.get();

            String runtime = this.contentsServiceMethod.runtimeByClassifyType(rs.getContent_id(), rs.getC_type());

            this.contentViewRepository.updateHitPointContent(rs.getContent_id());

            return ContentDetailResult.builder()
                    .contentId(rs.getContent_id())
                    .title(rs.getTitle())
                    .summary(rs.getSummary())
                    .cType(rs.getC_type())
                    .age(rs.getAge())
                    .released(rs.getReleased())
                    .contentImg(rs.getContent_img())
                    .genres(this.contentsServiceMethod.findGenresByContentId(rs.getContent_id()))
                    .runtime(runtime).build();
        }else{
            return ContentDetailResult.builder().build();
        }
    }

    @Override
    public List<PlatformResponse> findPlatformByContentId(String contentId){
        return this.contentPlatformRepository.findPlatformByContentId(contentId);
    }

    @Override
    public Page<ContentResponse> findContentsByGenre(PageRequest pageRequest, String genre){
        return this.contentRepository.findContentsByCategory(pageRequest, genre);
    }

    @Override
    public Page<ContentRequest> findContentsByCType(String orderBy, String cType, int nowPage, int pageSize){
        PageRequest pageRequest = this.contentsServiceMethod.pagingRequestMethod(orderBy, nowPage, pageSize);

        return this.contentsServiceMethod.pageResponseToRequest(this.contentRepository.findContentsByCType(pageRequest, cType),nowPage);
    }

    @Override
    public Page<ContentRequest> findContentsByTodayPick(){
        Page<ContentResponse> cr = this.contentRepository.findContentsByTodayPick(this.contentsServiceMethod.pagingRequestMethod("인기순",0,20));

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
