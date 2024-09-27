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
@Table(name = "inner_country")
public class InnerCountry {
    @Id
    @Column(name = "genre_id")
    private int genreId;

    @Column(name = "country")
    private String country;
}
