package com.ott.onde.content.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class ContentPlatform {
    @Id
    @GeneratedValue
    @Column(name = "content_platform_id")
    private String contentPlatformId;

    @Column(name = "platform_id")
    private String platformId;

    @Column(name = "content_id")
    private String contentId;

    @Column(name = "content_link")
    private String contentLink;

    @Column(name = "content_img")
    private String contentImg;
}
