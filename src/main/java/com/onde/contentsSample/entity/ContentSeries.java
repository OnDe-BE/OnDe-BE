package com.onde.contentsSample.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data@NoArgsConstructor
public class ContentSeries {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String innerSeriesId;

    private String seriesId;

    private String contentId;
}
