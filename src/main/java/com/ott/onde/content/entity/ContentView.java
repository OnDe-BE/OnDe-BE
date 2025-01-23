package com.ott.onde.content.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity@Data@NoArgsConstructor
public class ContentView {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userContentViewId;

    @ColumnDefault("0")
    private Long viewHour;
    private String contentId;
    private Long userIdx;
}
