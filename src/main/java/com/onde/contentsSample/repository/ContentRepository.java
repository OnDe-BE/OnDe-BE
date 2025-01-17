package com.onde.contentsSample.repository;

import com.onde.contentsSample.dto.ContentListResponse;
import com.onde.contentsSample.dto.ContentResponse;
import com.onde.contentsSample.entity.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContentRepository extends JpaRepository<Content, String> {
    @Query(value = "SELECT * from content where content_id = :contentId", nativeQuery = true)
    Content findByContentId(@Param("contentId") String contentId);

    @Query(value = "SELECT c.*, cp.content_img from " +
            "(select content_id, title, age from content where title REGEXP :contentTitle) as c, " +
            "content_platform as cp where c.content_id = cp.content_id limit 20", nativeQuery = true)
    Optional<List<ContentResponse>> findByTitle(@Param("contentTitle") String contentTitle);

//    컨텐츠 상세 조회
    @Query(value = "select c.content_id, c.title, c.age, c.released, c.summary, c.c_type, ig.genre, cp.content_img " +
            "from content as c, content_genre as cg, content_platform as cp, inner_genre as ig " +
            "where c.content_id = cp.content_id and cg.content_id = c.content_id and cg.genre_id = ig.genre_id and " +
            "c.content_id = :contentId group by genre", nativeQuery = true)
    Optional<List<ContentListResponse>> findContentsByContentId(@Param("contentId")String contentId);

    @Query(value = "select * from content as c " +
            "right outer join content_genre as cg on c.content_id = cg.content_id " +
            "left outer join inner_genre as ig on cg.genre_id = ig.genre_id " +
            "where genre REGEXP :category group by title", nativeQuery = true)
    Page<ContentResponse> findContentsByCategory(Pageable pageable, @Param("category") String category);

//    @Query(value = "select c.title, c.content_id, c.age, content_img from content as c " +
//            "right outer join content_platform as cp on c.content_id = cp.content_id " +
//            "right outer join content_genre as cg on c.content_id = cg.content_id " +
//            "right outer join inner_genre as ig on cg.genre_id = ig.genre_id " +
//            "where platform REGEXP :platform and genre REGEXP :category group by title order by :orderCategory desc limit :nowPage, :pageCount", nativeQuery = true)
//    List<ContentResponse> findContentsByPlatformAndCategory(@Param("orderCategory")String orderCategory, @Param("platform")String platform, @Param("category") String category, @Param("nowPage") int nowPage, @Param("pageCount")int pageCount);

    @Query(value = "select c.title, c.content_id, c.age, content_img from content as c " +
            "right outer join content_platform as cp on c.content_id = cp.content_id " +
            "right outer join content_genre as cg on c.content_id = cg.content_id " +
            "right outer join inner_genre as ig on cg.genre_id = ig.genre_id " +
            "where platform REGEXP :platform and genre REGEXP :category group by title", nativeQuery = true)
    Page<ContentResponse> findContentsByPlatformAndCategory(Pageable pageable, @Param("platform")String platform, @Param("category") String category);

    @Query(value = "select * from (select cg.genre_id, ct.title, ct.content_id, ct.age, row_number() over(partition by genre_id order by genre_id, ct.hit_point desc) rowNum " +
            "from content_genre as cg, content as ct where ct.content_id = cg.content_id) as c, " +
            "inner_genre as ig where ig.genre_id = c.genre_id " +
            "and ig.genre in :genres and rowNum < :groupNum group by c.title limit :nowPage, :pageCount",nativeQuery = true)
    List<ContentResponse> findContentsByGenre(@Param("genres") List<String> genres, @Param("groupNum") int groupNum , @Param("nowPage") int nowPage, @Param("pageCount") int pageCount);

    @Query(value = "select * from content as c " +
            "right outer join content_genre as cg on c.content_id = cg.content_id " +
            "left outer join inner_genre as ig on cg.genre_id = ig.genre_id " +
            "where genre REGEXP :category group by title", nativeQuery = true)
    List<ContentResponse> findContentsByTodayPick();
}
