package com.ott.onde.content.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "content_country")
public class ContentCountry {
    @Id
    @Column(name = "content_country_id")
    private long contentCountryId;

    @OneToMany
    @JoinColumn(name = "content_id")
    private ContentCountry contentCountry;

    @OneToMany
    @JoinColumn (name = "country_id")
    private InnerCountry innerCountry;
}
