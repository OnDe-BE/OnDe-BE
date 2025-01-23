package com.ott.onde.genre.entity;

import com.ott.onde.content.entity.Content;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "content_genre")
public class ContentGenre {
    @Id
    @Column(name = "content_genre_id", updatable = false)
    private String contentGenreId;

    @ManyToOne
    @JoinColumn(name = "content_id", nullable = false)
    private Content content;

    @ManyToOne
    @JoinColumn(name = "genre_id", nullable = false)
    private InnerGenre innerGenre;
}
