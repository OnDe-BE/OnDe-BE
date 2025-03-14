package com.ott.onde.content.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class InnerSeries {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "series_id")
    private String seriesId;

    private String title;

    private String summary;

    private String runtime;

    private String episode;

    private String img;
}
