package com.ott.onde.content.service;

import com.ott.onde.content.repository.CategorySortRepository;
import com.ott.onde.content.repository.InnerGenreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class CategoryService {
    private final CategorySortRepository categorySortRepository;
    private final InnerGenreRepository innerGenreRepository;

    public void insertWordCategory(String word, String dbWord){
        String categoryCode = "";

        Integer exist = this.innerGenreRepository.findGenreByGenre(dbWord);

        if(exist == 1){
            categoryCode = "GR";
        }else{
            categoryCode = "PF";
        }

        categoryCode = categoryCode+this.categorySortRepository.countByCategoryCode(categoryCode);

        this.categorySortRepository.insertWordCategory(categoryCode, word, dbWord);
    }

    public boolean findWordExist(String word){
        return this.categorySortRepository.findExistsByWord(word) == 1;
    }
}
