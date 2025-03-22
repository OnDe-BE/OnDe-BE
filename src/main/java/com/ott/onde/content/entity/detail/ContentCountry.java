//package com.ott.onde.content.entity;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Getter
//@Entity
//@NoArgsConstructor
//@Table(name = "content_country")
//public class ContentCountry {
//    @Id
//    @Column(name = "content_country_id")
//    private long contentCountryId;
//
//    @ManyToOne
//    @JoinColumn(name = "content_id", nullable = false)
//    private Content content;
//
//    @ManyToOne
//    @JoinColumn(name = "country_id", nullable = false)
//    private InnerCountry innerCountry;
//}
