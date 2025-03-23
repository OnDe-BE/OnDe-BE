package com.ott.onde.content.repository.user.genre;

import com.ott.onde.content.entity.user.genre.PreferSentence;
import com.ott.onde.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PreferSentenceRepository extends JpaRepository<PreferSentence, Long> {
    List<PreferSentence> findByUser(User user);
}
