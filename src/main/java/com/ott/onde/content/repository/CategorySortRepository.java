package com.ott.onde.content.repository;


import com.ott.onde.content.entity.CategorySort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategorySortRepository extends JpaRepository<CategorySort, String> {
    @Query(value = "SELECT * FROM category_sort WHERE word like :word",nativeQuery = true)
    List<CategorySort> findByWord(@Param("word")String word);
}
