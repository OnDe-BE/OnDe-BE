package com.ott.onde.content.service.util;

import com.ott.onde.content.dto.request.ContentRequest;
import com.ott.onde.content.entity.PreferSentence;
import com.ott.onde.content.repository.genre.PreferGenreRepository;
import com.ott.onde.content.repository.genre.PreferSentenceRepository;
import com.ott.onde.content.service.ContentsServiceMethod;
import com.ott.onde.content.service.crud.ContentSimpleService;
import com.ott.onde.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserPreferContent {
    private final PreferGenreRepository preferGenreRepository;
    private final PreferSentenceRepository preferSentenceRepository;
    private final ContentsServiceMethod contentsServiceMethod;
    private final ContentSimpleService contentSimpleService;



//    public List<String> preferGenreSorting(User user){
//        return this.preferGenreRepository.findByUserCode(user.getUserCode());
//    }
//
//    public List<String> genreSorting(List<String> sentence, List<String> genre){
//        sentence.addAll(genre);
//
//        return sentence.stream().distinct().collect(Collectors.toList());
//    }

    public Page<ContentRequest> recommendedContent(User user){
        List<PreferSentence> preferSentences = this.preferSentenceRepository.findByUser(user);

        PreferSentence preferSentence = preferSentences.get((int)(Math.random() * preferSentences.size()-1));

        List<String> genres = this.preferSentenceSorting(preferSentence);
        PageRequest pageRequest = PageRequest.of(0, 20);

        log.info("preferGenre: {}, preferSentence : {}",genres.get(0), preferSentence.getPreferSentence());

        Page< ContentRequest> page =  this.contentsServiceMethod.pageResponseToRequest(this.contentSimpleService.findContentsByGenre(pageRequest, genres.get(0)),1);

        return page;
    }

    public List<String> preferSentenceSorting(PreferSentence preferSentence) {
        List<String> result = new ArrayList<>();

        for(String str : preferSentence.getPreferSentence().split(" ")){
            result.add(this.contentsServiceMethod.sentenceSorting(str).get("genre"));
        }

        return result;
    }
}
