package com.onde.contentsSample.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class ContentGenre {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String contentGenreId;

    private String contentId;

}