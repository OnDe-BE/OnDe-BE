package com.ott.onde.content.repository;


import com.ott.onde.content.entity.InnerGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InnerGenreRepository extends JpaRepository<InnerGenre, Long> {
    @Query(value = "select ig.genre from inner_genre as ig, " +
            "(select * from content_genre where content_id = :contentId) as cg " +
            "where ig.genre_id = cg.genre_id", nativeQuery = true)
    List<String> findGenreByContentId(@Param("contentId")String contentId);
}
