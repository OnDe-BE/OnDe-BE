package com.ott.onde.content.entity;

import com.ott.onde.genre.entity.ContentGenre;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "content")
    private List<ContentGenre> contentGenres = new ArrayList<>();
}
