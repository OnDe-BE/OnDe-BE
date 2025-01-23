package com.ott.onde.content.entity;

import com.ott.onde.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "content_country")
public class ContentCountry {
    @Id
    @Column(name = "content_country_id")
    private long contentCountryId;

    @ManyToOne
    @JoinColumn(name = "content_id", nullable = false)
    private Content content;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private InnerCountry innerCountry;
}
