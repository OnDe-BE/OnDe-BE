package com.ott.onde.genre.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "inner_genre")
public class InnerGenre {
    @Id
    @Column(name = "genre_id", updatable = false)
    private String genreId;

    @Column(name = "genre", nullable = false)
    private String genre;
}
