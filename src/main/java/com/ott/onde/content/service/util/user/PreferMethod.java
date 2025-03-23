package com.ott.onde.content.service.util.user;

import com.ott.onde.content.entity.user.genre.PreferSentence;
import com.ott.onde.content.entity.user.ContentLike;
import com.ott.onde.user.entity.User;

import java.util.List;

public interface PreferMethod {
    public String getInteresetedGenre(User user);
    public PreferSentence getRecommendedSentence(User user);
    public String getRandomGenre(List<String> genres);

//    select contents by user method
    public ContentLike checkUserLikeContents(User user);
    public ContentLike checkUserLikeContents(User user, String contentId);
    public List<ContentLike> findUserLikeContents(User user);

//    like contents by user method
    public void updateUserLikeContents(User user, String contentId);
    public void insertUserLikeContents(User user, String contentId);
    public void deleteUserLikeContents(User user, String contentId);
}
