package com.ott.onde.content.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@NoArgsConstructor
@Data
public class Content {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String contentId;

    private String title;

    private String summary;

    private String cType;

    private String age;

    private String released;

    @ColumnDefault("0")
    private Long hitPoint;
}
