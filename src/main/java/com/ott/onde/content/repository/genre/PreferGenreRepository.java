package com.ott.onde.content.repository.genre;

import com.ott.onde.content.entity.PreferGenre;
import com.ott.onde.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PreferGenreRepository extends JpaRepository<PreferGenre, Long> {
    List<PreferGenre> findByUser(User user);

    @Query(value = "select ig.genre from prefer_genre as pg, inner_genre as ig " +
            "where user_code = :userCode and pg.genre = ig.genre_id",nativeQuery = true)
    List<String> findByUserCode(@Param("userCode") String userCode);
}
