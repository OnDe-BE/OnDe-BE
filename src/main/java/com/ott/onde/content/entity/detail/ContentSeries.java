package com.ott.onde.content.entity.detail;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data@NoArgsConstructor
public class ContentSeries {
    @Id@GeneratedValue
    @Column(name = "inner_series_id")
    private String innerSeriesId;

    @Column(name = "series_id")
    private String seriesId;

    @Column(name = "content_id")
    private String contentId;
}
