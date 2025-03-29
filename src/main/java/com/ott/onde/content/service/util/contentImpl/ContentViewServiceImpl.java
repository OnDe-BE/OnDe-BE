package com.ott.onde.content.service.util.contentImpl;

import com.ott.onde.content.dto.request.ContentRequest;
import com.ott.onde.content.entity.user.ContentLinkView;
import com.ott.onde.content.entity.user.ContentView;
import com.ott.onde.content.repository.user.ContentLinkViewRepository;
import com.ott.onde.content.repository.user.ContentViewRepository;
import com.ott.onde.content.service.util.user.ContentViewService;
import com.ott.onde.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ContentViewServiceImpl implements ContentViewService {
    private final ContentViewRepository contentViewRepository;
    private final ContentLinkViewRepository contentLinkViewRepository;

    @Override
    public void linkViewHitPoint(User user, String contentId, String platform) {
        this.contentLinkViewRepository.save(ContentLinkView.builder().contentId(contentId)
                .userCode(user != null ? user.getUserCode() : "guest")
                .platform(platform)
                .build());
    }

    @Override
    public void contentViewHitPoint(User user, String contentId) {
        this.contentViewRepository.save(ContentView.builder()
                        .contentId(contentId)
                        .user(user)
                        .viewHour(0L)
                .build());
    }

    @Override
    public void contentHitPoint(User user, String contentId) {

    }

    @Override
    public List<ContentRequest> findRecentViewContentsByUser(User user) {
        List<ContentView> contents = this.contentViewRepository.findByUser(user);



        return List.of();
    }

    @Override
    public ContentView findContentViewByContentId(User user, String contentId) {
        ContentView contentView = this.contentViewRepository.findByUserAndContentId(user, contentId);

        return contentView;
    }

}
