package com.ott.onde.content.repository;


import com.ott.onde.content.dto.PlatformResponse;
import com.ott.onde.content.entity.ContentPlatform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentPlatformRepository extends JpaRepository<ContentPlatform,String> {
    @Query(value = "select platform, content_link from content_platform where content_id = :contentId", nativeQuery = true)
    List<PlatformResponse> findPlatformByContentId(@Param("contentId") String contentId);
}
