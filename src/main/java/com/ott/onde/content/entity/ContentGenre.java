package com.ott.onde.content.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class ContentGenre {
    @Id@GeneratedValue
    private String contentGenreId;

    private String contentId;

    private int genreId;
}