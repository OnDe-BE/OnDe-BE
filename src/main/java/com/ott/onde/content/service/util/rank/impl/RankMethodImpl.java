package com.ott.onde.content.service.util.rank.impl;

import com.ott.onde.content.repository.util.ContentRankRepository;
import com.ott.onde.content.repository.ContentRepository;
import com.ott.onde.content.service.util.rank.RankMethod;
import com.ott.onde.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RankMethodImpl implements RankMethod {
    private final ContentRankRepository contentRankRepository;
    private final ContentRepository contentRepository;

    @Override
    public void updateRankByUserContentLike(User user, String contentId) {

    }

    @Override
    public void updateRankByUserContentView(User user, String contentId) {

    }

    @Override
    public void updateRankByUserContentLinkView(User user, String contentPlatformId) {

    }

    @Override
    public void updateRankGenreByUserView() {

    }
}
