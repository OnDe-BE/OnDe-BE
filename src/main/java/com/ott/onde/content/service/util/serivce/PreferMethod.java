package com.ott.onde.content.service.util.serivce;

import com.ott.onde.content.entity.genre.PreferSentence;
import com.ott.onde.user.entity.User;

import java.util.List;

public interface PreferMethod {
    public String getInteresetedGenre(User user);
    public PreferSentence getRecommendedSentence(User user);
    public String getRandomGenre(List<String> genres);
}
