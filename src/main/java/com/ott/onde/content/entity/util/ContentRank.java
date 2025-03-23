package com.ott.onde.content.entity.util;

import com.ott.onde.content.entity.Content;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "content_rank")
public class ContentRank {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "content_id")
    private Content content;

    private int point;

    @Builder
    public ContentRank(Long id, Content content, int point) {
        this.content = content;
        this.point = point;
    }
}
