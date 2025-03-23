package com.ott.onde.content.repository;

import com.ott.onde.content.dto.response.ContentDetailResponse;
import com.ott.onde.content.dto.response.ContentIdResponse;
import com.ott.onde.content.dto.response.ContentResponse;
import com.ott.onde.content.entity.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContentRepository extends JpaRepository<Content, String> {
    @Query(value = "SELECT * from content where content_id = :contentId", nativeQuery = true)
    Content findByContentId(@Param("contentId") String contentId);

    @Query(value = "select c.content_id, c.title, c.age, c.content_img  from content as c", nativeQuery = true)
    Page<ContentResponse> findContentsAll(Pageable pageable);

    @Query(value = "SELECT c.content_id, c.title, c.age, c.content_img from content as c " +
            "title REGEXP :contentTitle", nativeQuery = true)
    Optional<Page<ContentResponse>> findByTitle(Pageable pageable, @Param("contentTitle") String contentTitle);

//    컨텐츠 상세 조회
//    @Query(value = "select c.content_id, c.title, c.age, c.released, c.summary, c.c_type, c.content_img " +
//            "from content as c where c.content_id = :contentId", nativeQuery = true)
//    Optional<ContentDetailResponse> findContentsByContentId(@Param("contentId")String contentId);
    Optional<Content> findContentsByContentId(String contentId);

    @Query(value = "select c.content_id, c.title, c.age from content as c , " +
            "(select content_id, count(*) as relevance from content_genre as cg " +
            "left outer join inner_genre as ig on cg.genre_id = ig.genre_id " +
            "where ig.genre REGEXP :category group by content_id) as rel " +
            "where c.content_id = rel.content_id order by relevance desc", nativeQuery = true)
    Page<ContentResponse> findContentsByCategory(Pageable pageable, @Param("category") String category);

    @Query(value = "select c.title, c.content_id, c.age, c.content_img from content as c " +
            "right outer join content_platform as cp on c.content_id = cp.content_id " +
            "where cp.platform REGEXP :platform", nativeQuery = true)
    Page<ContentResponse> findContentsByPlatform(Pageable pageable, @Param("platform")String platform);

    @Query(value = "select c.title, c.content_id, c.age, c.content_img from content as c " +
            "where title REGEXP :category or summary REGEXP :category", nativeQuery = true)
    Page<ContentResponse> findContentsByTitleAndSummary(Pageable pageable, @Param("category") String category);

    @Query(value = "select * from content as c", nativeQuery = true)
    Page<ContentResponse> findContentsByTodayPick(Pageable pageable);

    @Query(value = "select c.title, c.content_id, c.age, c.content_img from content as c " +
            "where age REGEXP :category or summary REGEXP :age", nativeQuery = true)
    Page<ContentResponse> findContentByAge(@Param("age") String age, Pageable pageable);

    @Query(value = "select c.title, c.content_id, c.age, c.content_img from content as c " +
            "where released between :startReleased and :endReleased", nativeQuery = true)
    Page<ContentResponse> findContentByReleased(@Param("startReleased")int startReleased, @Param("endReleased")int endReleased, Pageable pageable);

//    type sort
    @Query(value = "select c.title, c.content_id, c.age, c.content_img from content as c " +
            "where c_type = :cType", nativeQuery = true)
    Page<ContentResponse> findContentsByCType(Pageable pageAble, @Param("cType")String cType);

// -----------------------------------------------------categorySorting Query

    @Query(value = "select c.content_id, c.title, c.age from content as c , " +
            "(select content_id, count(*) as relevance from content_genre as cg " +
            "left outer join inner_genre as ig on cg.genre_id = ig.genre_id " +
            "where ig.genre REGEXP :category group by content_id) as rel " +
            "where c.content_id = rel.content_id order by relevance desc", nativeQuery = true)
    List<ContentResponse> findContentsByCategory(@Param("category") String category);

    @Query(value = "select c.title, c.content_id, c.age, c.content_img from content as c " +
            "right outer join content_platform as cp on c.content_id = cp.content_id " +
            "where platform REGEXP :platform and c.content_id IN (:contentId)", nativeQuery = true)
    Page<ContentResponse> findContentByPlatformsAndContentId(Pageable pageable, @Param("platform")String platform, @Param("contentId") List<String> category);

    @Query(value = "select content_id, count(*) as relevance from content_genre as cg, inner_genre as ig " +
            "where cg.genre_id = ig.genre_id and ig.genre REGEXP :category group by content_id order by relevance desc",nativeQuery = true)
    List<ContentIdResponse> findContentIdByCategory(@Param("category")String category);

    @Query(value = "select c.title, c.content_id, c.age from content as c " +
            "where c.content_id IN (:contentId) and c.age regexp :age and released between :start and :end and c.c_type regexp :cType", nativeQuery = true)
    Page<ContentResponse> findContentsByContentIdAndAgeAndReleasedAndCType(Pageable pageable, @Param("contentId") List<String> contentId, @Param("age")String age, @Param("start") int start, @Param("end") int end, @Param("cType")String cType);
}
