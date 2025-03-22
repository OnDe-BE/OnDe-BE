package com.ott.onde.content.entity.util;

import com.ott.onde.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity@Data@NoArgsConstructor
public class ContentView {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ColumnDefault("0")
    @Column(name = "view_hour")
    private Long viewHour;

    @Column(name = "content_id")
    private String contentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_code")
    private User user;

    @Builder
    public ContentView(Long viewHour, String contentId, User user) {
        this.viewHour = viewHour;
        this.contentId = contentId;
        this.user = user;
    }
}
