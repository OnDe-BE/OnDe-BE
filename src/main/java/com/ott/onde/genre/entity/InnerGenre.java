package com.ott.onde.genre.entity;

import com.ott.onde.content.entity.ContentCountry;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "innerGenre")
    private List<ContentGenre> contentGenres = new ArrayList<>();
}
