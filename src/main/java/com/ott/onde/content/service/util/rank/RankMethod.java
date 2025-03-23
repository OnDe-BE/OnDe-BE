package com.ott.onde.content.service.util.rank;

import com.ott.onde.user.entity.User;

public interface RankMethod {
    public void updateRankByUserContentLike(User user, String contentId);
    public void updateRankByUserContentView(User user, String contentId);
    public void updateRankByUserContentLinkView(User user, String contentPlatformId);
    public void updateRankGenreByUserView();
}
