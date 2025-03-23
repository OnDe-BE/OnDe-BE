package com.ott.onde.content.repository.user;

import com.ott.onde.content.entity.user.ContentView;
import com.ott.onde.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ContentViewRepository extends JpaRepository<ContentView, Long> {
    @Query(value = "update content set hit_point = hit_point+1 where content_id = :contentId", nativeQuery = true)
    @Modifying@Transactional
    void updateHitPointContent(@Param("contentId")String contentId);

    @Query(value = "update content_view set view_hour = :viewHour where content_id = :contentId and user_idx = :userIdx", nativeQuery = true)
    @Modifying@Transactional
    void updateViewHourContentView(@Param("viewHour")Long viewHour, @Param("contentId")String contentId, @Param("userIdx")Long userIdx);

    List<ContentView> findByUser(User user);
    ContentView findByUserAndContentId(User user, String contentId);
}
