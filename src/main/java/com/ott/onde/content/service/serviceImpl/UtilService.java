package com.ott.onde.content.service.serviceImpl;

import com.ott.onde.content.entity.genre.InnerGenre;

import java.util.List;

public interface UtilService {
    public List<String> findRecommendGenres();
    public List<String> findRecommendSentences();
    public List<InnerGenre> findGenres();
}
