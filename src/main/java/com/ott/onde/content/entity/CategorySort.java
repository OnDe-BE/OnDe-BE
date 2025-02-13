package com.ott.onde.content.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity@NoArgsConstructor@Data
public class CategorySort {
    @Id
    private String categoryCode;

    @Column
    private String word;

    @Column
    private String dbWord;

}
