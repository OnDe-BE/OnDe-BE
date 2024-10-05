package com.ott.onde.genre.repository;

import com.ott.onde.genre.entity.PreferGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferGenreRepository extends JpaRepository<PreferGenre, Long> {
}
