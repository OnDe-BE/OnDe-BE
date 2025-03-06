package com.ott.onde.content.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class ContentPlatform {
    @Id
    @GeneratedValue
    private String contentPlatformId;

    private String platformId;

    private String contentId;

    private String contentLink;

    private String contentImg;
}
