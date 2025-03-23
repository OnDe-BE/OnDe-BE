package com.ott.onde.content.service.util.user.impl;

import com.ott.onde.content.entity.user.genre.PreferGenre;
import com.ott.onde.content.entity.user.genre.PreferSentence;
import com.ott.onde.content.entity.user.ContentLike;
import com.ott.onde.content.repository.user.ContentLikeRepository;
import com.ott.onde.content.repository.user.genre.PreferGenreRepository;
import com.ott.onde.content.repository.user.genre.PreferSentenceRepository;
import com.ott.onde.content.service.util.method.ContentsServiceMethod;
import com.ott.onde.content.service.util.user.PreferMethod;
import com.ott.onde.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class PreferMethodImpl implements PreferMethod {
    private final PreferSentenceRepository preferSentenceRepository;
    private final PreferGenreRepository preferGenreRepository;
    private final ContentsServiceMethod contentsServiceMethod;
    private final ContentLikeRepository contentLikeRepository;

    @Override
    public String getInteresetedGenre(User user) {
    
        List<PreferSentence> preferSentences = this.preferSentenceRepository.findByUser(user);
        List<String> genres = new ArrayList<>(this.preferGenreRepository.findByUser(user).stream().map(PreferGenre::getGenre).toList());

        for (List<String> strings : preferSentences.stream().map(this::preferSentenceSorting).toList()) {
            strings.stream().filter(Objects::nonNull).forEach(genres::add);
        }

        return this.getRandomGenre(genres);
    }

    @Override
    public PreferSentence getRecommendedSentence(User user) {
        List<PreferSentence> preferSentence = this.preferSentenceRepository.findByUser(user);

        PreferSentence result = preferSentence.get((int)(Math.random() * preferSentence.size() - 1));

        return result;
    }

    @Override
    public String getRandomGenre(List<String> genres) {
        String genre = genres.get((int)(Math.random() * genres.size() - 1));

        log.info("return genre: {}", genre);

        return genre;
    }

    @Override
    public ContentLike checkUserLikeContents(User user) {
        List<ContentLike> contentLikes = this.findUserLikeContents(user);

        return contentLikes.get(19);
    }

    @Override
    public ContentLike checkUserLikeContents(User user,String contentId) {
        Optional<ContentLike> contentLikes = this.contentLikeRepository.findByUserAndContentId(user,contentId);

        return contentLikes.orElse(null);
    }

    @Override
    public List<ContentLike> findUserLikeContents(User user) {
        List<ContentLike> contentLikes = this.contentLikeRepository.findByUserOrderByCreatedAt(user);

        return contentLikes.isEmpty() ? new ArrayList<>() : contentLikes;
    }

    @Override
    public void updateUserLikeContents(User user, String contentId) {
        ContentLike beforeCl = this.checkUserLikeContents(user);

        this.contentLikeRepository.delete(beforeCl);

        this.insertUserLikeContents(user,contentId);
    }

    @Override
    public void insertUserLikeContents(User user, String contentId) {
        ContentLike contentLike = this.checkUserLikeContents(user,contentId);

        contentLike.setUser(user);
        contentLike.setContentId(contentId);

        this.contentLikeRepository.save(contentLike);
    }

    @Override
    public void deleteUserLikeContents(User user, String contentId) {
        ContentLike contentLike = this.checkUserLikeContents(user,contentId);

        this.contentLikeRepository.delete(contentLike);
    }

    public List<String> preferSentenceSorting(PreferSentence preferSentence) {
        List<String> result = new ArrayList<>();

        for(String str : preferSentence.getPreferSentence().split(" ")){
            result.add(this.contentsServiceMethod.sentenceSorting(str).get("genre"));
        }

        return result;
    }


}
