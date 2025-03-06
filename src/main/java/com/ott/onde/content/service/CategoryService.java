package com.ott.onde.content.service;

import com.ott.onde.content.entity.InnerGenre;
import com.ott.onde.content.repository.CategorySortRepository;
import com.ott.onde.content.repository.genre.InnerGenreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class CategoryService {
    private final CategorySortRepository categorySortRepository;
    private final InnerGenreRepository innerGenreRepository;

    public void insertWordCategory(String word, String dbWord){
        String categoryCode = "";

        Optional<InnerGenre> genre = this.innerGenreRepository.findGenreByGenre(dbWord);

        categoryCode = genre.isPresent() ? "GR" : "PF";

        categoryCode = categoryCode+this.categorySortRepository.countByCategoryCode(categoryCode);

        this.categorySortRepository.insertWordCategory(categoryCode, word, dbWord);
    }

    public boolean findWordExist(String word){
        return this.categorySortRepository.findExistsByWord(word) == 1;
    }
}
