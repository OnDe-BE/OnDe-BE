package com.ott.onde.content.service.util.contentImpl;

import com.ott.onde.content.dto.request.ContentRequest;
import com.ott.onde.content.dto.result.ContentPreferResult;
import com.ott.onde.content.entity.genre.PreferSentence;
import com.ott.onde.content.service.ContentsServiceMethod;
import com.ott.onde.content.service.serviceImpl.ContentDetailServiceImpl;
import com.ott.onde.content.service.serviceImpl.ContentSimpleServiceImpl;
import com.ott.onde.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserPreferContent {
    private final PreferMethodImpl preferMethod;
    private final ContentsServiceMethod contentsServiceMethod;
    private final ContentSimpleServiceImpl contentSimpleServiceImpl;
    private final ContentDetailServiceImpl contentDetailServiceImpl;

    public ContentPreferResult recommendedContent(User user){
        String preferGenre = this.preferMethod.getInteresetedGenre(user);

        PageRequest pageRequest = PageRequest.of(0, 20);

        Page< ContentRequest> page =  this.contentsServiceMethod.pageResponseToRequest(this.contentSimpleServiceImpl.findContentsByGenre(pageRequest, preferGenre),1);

        return ContentPreferResult.builder().contents(page).titleTypes(preferGenre).build();
    }

    public ContentPreferResult recommendedSentence(User user){
        PreferSentence preferSentence = this.preferMethod.getRecommendedSentence(user);

        Page<ContentRequest> page = this.contentDetailServiceImpl.findSearchContentsByMultiCategory("인기순", preferSentence.getPreferSentence(), 0, 20);

        return ContentPreferResult.builder().contents(page).titleTypes(preferSentence.getPreferSentence()).build();
    }
}
