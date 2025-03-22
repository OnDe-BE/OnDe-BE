package com.ott.onde.content.service;

import com.ott.onde.content.entity.genre.InnerGenre;
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
        String category_code = "";

        Optional<InnerGenre> genre = this.innerGenreRepository.findGenreByGenre(dbWord);

        category_code = genre.isPresent() ? "genre" : "platform";

        category_code = category_code+this.categorySortRepository.countByCategoryCode(category_code);

        this.categorySortRepository.insertWordCategory(category_code, word, dbWord);
    }

    public boolean findWordExist(String word){
        return this.categorySortRepository.findExistsByWord(word) == 1;
    }
}
