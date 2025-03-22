package com.ott.onde.content.service.util.contentImpl;

import com.ott.onde.content.entity.util.ContentLinkView;
import com.ott.onde.content.entity.util.ContentView;
import com.ott.onde.content.repository.ContentLinkViewRepository;
import com.ott.onde.content.repository.ContentPlatformRepository;
import com.ott.onde.content.repository.ContentViewRepository;
import com.ott.onde.content.service.util.serivce.ContentViewService;
import com.ott.onde.user.entity.User;
import com.ott.onde.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class ContentViewServiceImpl implements ContentViewService {
    private final ContentViewRepository contentViewRepository;
    private final UserRepository userRepository;
    private final ContentPlatformRepository contentPlatformRepository;
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
}
