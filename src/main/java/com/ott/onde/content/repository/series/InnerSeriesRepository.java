package com.ott.onde.content.repository.series;

import com.ott.onde.content.entity.InnerSeries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InnerSeriesRepository extends JpaRepository<InnerSeries, String> {
    @Query(value = "select runtime from content_series as cs , inner_series as is" +
            "where cs.series_id = is.series_id and cs.content_id = :contentId limit 1", nativeQuery = true)
    String findRuntimeByContentId(String contentId);
}
