package com.ott.onde.content.repository.genre;

import com.ott.onde.content.dto.request.GenreRequest;
import com.ott.onde.content.entity.genre.ContentGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentGenreRepository extends JpaRepository<ContentGenre, String> {
    @Query(value = "select ig.genre from content_genre as cg, inner_genre as ig " +
            "where cg.content_id = :contentId and cg.genre_id = ig.genre_id",nativeQuery = true)
    List<String> findGenreByContentId(@Param("contentId") String contentId);

    @Query(value = "select cg.content_id, ig.genre from content_genre as cg, inner_genre as ig " +
            "where cg.content_id in (:contentId) and cg.genre_id = ig.genre_id",nativeQuery = true)
    List<GenreRequest> findGenreByContentId(@Param("contentId") List<String> contentId);
}
