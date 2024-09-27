package com.ott.onde.genre.entity;

import com.ott.onde.content.entity.Content;
import com.ott.onde.content.entity.ContentCountry;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "content_genre")
public class ContentGenre {
    @Id
    @Column(name = "content_genre_id", updatable = false)
    private String contentGenreId;

    @ManyToOne
    @JoinColumn(name = "content_id", updatable = false)
    private Content content;

    @ManyToOne
    @JoinColumn(name="country_id", updatable = false)
    private ContentCountry country;
}
