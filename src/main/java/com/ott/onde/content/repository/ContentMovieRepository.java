package com.ott.onde.content.repository;

import com.ott.onde.content.entity.ContentMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContentMovieRepository extends JpaRepository<ContentMovie, String> {
    @Query(value = "select runtime from content_movie where content_id = :contentId", nativeQuery = true)
    String findRuntimeByContentId(@Param("contentId") String contentId);
}
