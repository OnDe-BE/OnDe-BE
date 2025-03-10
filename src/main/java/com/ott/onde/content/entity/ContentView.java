package com.ott.onde.content.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity@Data@NoArgsConstructor
public class ContentView {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_content_view_id")
    private Long userContentViewId;

    @ColumnDefault("0")
    @Column(name = "view_hour")
    private Long viewHour;

    @Column(name = "content_id")
    private String contentId;

    @Column(name = "user_code")
    private String userCode;
}
