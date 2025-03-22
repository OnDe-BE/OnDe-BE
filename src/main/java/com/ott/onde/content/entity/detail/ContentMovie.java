package com.ott.onde.content.entity.detail;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class ContentMovie {
    @Id@Column(name = "movie_id")
    private String movieId;

    @Column(name = "content_id")
    private String contentId;

    private String runtime;
}
