package com.ott.onde.content.repository.util;

import com.ott.onde.content.entity.Content;
import com.ott.onde.content.entity.util.ContentRank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRankRepository extends JpaRepository<ContentRank, Long> {
    ContentRank findByContent(Content content);
}
