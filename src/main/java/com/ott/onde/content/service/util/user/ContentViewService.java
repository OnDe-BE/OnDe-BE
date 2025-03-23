package com.ott.onde.content.service.util.user;

import com.ott.onde.content.dto.request.ContentRequest;
import com.ott.onde.content.entity.user.ContentView;
import com.ott.onde.user.entity.User;

import java.util.List;

public interface ContentViewService {
    public void linkViewHitPoint(User user, String contentId, String platform);

//    user can find recent view contents by this table
    public void contentViewHitPoint(User user, String contentId);
//    user/guest if click this content details
    public void contentHitPoint(User user, String contentId);

    public List<ContentRequest> findRecentViewContentsByUser(User user);

//    DB auto delete 7 days contentView by createdAt
    public ContentView findContentViewByContentId(User user, String contentId);
}
