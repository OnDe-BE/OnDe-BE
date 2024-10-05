package com.ott.onde.content.entity;

import com.ott.onde.genre.entity.ContentGenre;
import com.ott.onde.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "inner_country")
public class InnerCountry {
    @Id
    @Column(name = "country_id")
    private int countryId;

    @Column(name = "country")
    private String country;

    @OneToMany(mappedBy = "innerCountry")
    private List<ContentCountry> contentCountries = new ArrayList<>();
}
