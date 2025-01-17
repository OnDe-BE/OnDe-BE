package com.onde.contentsSample.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class InnerGenre {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int genreId;

    private String genre;
}
