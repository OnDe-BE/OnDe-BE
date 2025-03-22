package com.ott.onde.content.service.serviceImpl;

import com.ott.onde.content.entity.genre.InnerGenre;
import com.ott.onde.content.entity.genre.PreferSentence;
import com.ott.onde.content.repository.genre.InnerGenreRepository;
import com.ott.onde.content.repository.genre.PreferSentenceRepository;
import com.ott.onde.content.service.crud.UtilService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class UtilServiceImpl implements UtilService {
    private final InnerGenreRepository genreRepository;
    private final PreferSentenceRepository sentenceRepository;

    @Override
    public List<String> findRecommendGenres() {
        List<String> genres = this.genreRepository.findAll().stream().map(InnerGenre::getGenre).toList();

        return genres;
    }

    @Override
    public List<String> findRecommendSentences() {
        List<String> sentences = this.sentenceRepository.findAll().stream().map(PreferSentence::getPreferSentence).toList();

        return sentences;
    }

    @Override
    public List<InnerGenre> findGenres(){
        return this.genreRepository.findAll();
    }
}
