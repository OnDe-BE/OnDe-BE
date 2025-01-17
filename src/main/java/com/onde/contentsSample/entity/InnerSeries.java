package com.onde.contentsSample.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class InnerSeries {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String innerSeriesId;

    private String seriesTitle;

    private String seriesSummary;

    private String seriesRuntime;

    private String seriesEpisode;

    private String seriesImg;
}
