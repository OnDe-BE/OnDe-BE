package com.ott.onde.genre.repository;

import com.ott.onde.genre.entity.InnerGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InnerGenreRepository extends JpaRepository<InnerGenre, String> {
}