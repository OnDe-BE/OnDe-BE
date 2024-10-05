package com.ott.onde.genre.repository;

import com.ott.onde.genre.entity.ContentGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentGenreRepository extends JpaRepository<ContentGenre, String> {
}
