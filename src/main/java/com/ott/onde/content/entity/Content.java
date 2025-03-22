package com.ott.onde.content.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@NoArgsConstructor
@Data
public class Content {
    @Id@GeneratedValue
    @Column(name = "content_id")
    private String contentId;

    private String title;

    private String summary;

    @Column(name = "c_type")
    private String cType;

    private String age;

    private String released;

    @Column(name = "content_img")
    private String contentImg;

    @ColumnDefault("0")
    @Column(name = "hit_point")
    private Long hitPoint;
}
