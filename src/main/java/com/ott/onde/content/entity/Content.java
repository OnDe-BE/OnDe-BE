package com.ott.onde.content.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "content")
public class Content {
    @Id
    @Column(name = "content_id", updatable = false)
    private String contentId;

    @Column(name = "title")
    private String title;

    @Column(name = "summary")
    private String summary;

    @Column(name = "type")
    private String type;

    @Column(name = "age")
    private String age;

    @Column(name = "released")
    private String released;
}
