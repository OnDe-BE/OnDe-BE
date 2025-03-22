package com.ott.onde.content.entity.genre;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class ContentGenre {
    @Id@GeneratedValue
    @Column(name = "content_genre_id")
    private String contentGenreId;

    @Column(name = "content_id")
    private String contentId;

    @Column(name = "genre_id")
    private int genreId;
}