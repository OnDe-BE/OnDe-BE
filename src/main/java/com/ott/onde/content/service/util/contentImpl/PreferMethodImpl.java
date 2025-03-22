package com.ott.onde.content.service.util.contentImpl;

import com.ott.onde.content.entity.genre.PreferGenre;
import com.ott.onde.content.entity.genre.PreferSentence;
import com.ott.onde.content.repository.genre.PreferGenreRepository;
import com.ott.onde.content.repository.genre.PreferSentenceRepository;
import com.ott.onde.content.service.ContentsServiceMethod;
import com.ott.onde.content.service.util.serivce.PreferMethod;
import com.ott.onde.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Slf4j
public class PreferMethodImpl implements PreferMethod {
    private final PreferSentenceRepository preferSentenceRepository;
    private final PreferGenreRepository preferGenreRepository;
    private final ContentsServiceMethod contentsServiceMethod;

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

    public List<String> preferSentenceSorting(PreferSentence preferSentence) {
        List<String> result = new ArrayList<>();

        for(String str : preferSentence.getPreferSentence().split(" ")){
            result.add(this.contentsServiceMethod.sentenceSorting(str).get("genre"));
        }

        return result;
    }


}
