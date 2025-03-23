package com.ott.onde.content.entity.util;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity@NoArgsConstructor@Data
public class CategorySort {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_idx")
    private Long categoryIdx;

    private String word;

    @Column(name = "db_word")
    private String dbWord;

    @Column(name = "category")
    private String category;

}
