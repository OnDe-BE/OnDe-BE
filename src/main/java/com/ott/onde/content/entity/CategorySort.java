package com.ott.onde.content.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity@NoArgsConstructor@Data
public class CategorySort {
    @Id
    @Column(name = "category_code")
    private String categoryCode;

    private String word;

    @Column(name = "db_word")
    private String dbWord;

}
