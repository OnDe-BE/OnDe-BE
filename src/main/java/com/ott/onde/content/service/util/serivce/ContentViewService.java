package com.ott.onde.content.service.util.serivce;

import com.ott.onde.user.entity.User;

public interface ContentViewService {
    public void linkViewHitPoint(User user, String contentId, String platform);

//    user can find recent view contents by this table
    public void contentViewHitPoint(User user, String contentId);
//    user/guest if click this content details
    public void contentHitPoint(User user, String contentId);
}
