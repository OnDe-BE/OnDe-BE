package com.ott.onde.content.repository;

import com.ott.onde.content.entity.CategorySort;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategorySortRepository extends JpaRepository<CategorySort, String> {
    @Query(value = "SELECT EXISTS (select * from category_sort where word = :word)",nativeQuery = true)
    Integer findExistsByWord(@Param("word") String word);

    @Query(value = "SELECT * FROM category_sort WHERE word like :word",nativeQuery = true)
    List<CategorySort> findByWord(String word);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO category_sort SET category_code = :category_code, word = :word, db_word = :db_word", nativeQuery = true)
    void insertWordCategory(@Param("category_code")String category_code, @Param("word")String word, @Param("db_word")String db_word);

    @Query(value = "SELECT count(*) from category_sort where category_code REGEXP :category_code",nativeQuery = true)
    Integer countByCategoryCode(@Param("category_code")String category_code);
}
