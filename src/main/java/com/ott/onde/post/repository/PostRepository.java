package com.ott.onde.post.repository;

import com.ott.onde.post.entity.BoardKind;
import com.ott.onde.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByBoardKind(BoardKind boardKind);
    List<Post> findAllByBoardKindOrderByPostViewsDesc(BoardKind boardKind);   // 조회순
    List<Post> findAllByBoardKindOrderByCreatedAtDesc(BoardKind boardKind); // 최신순
    List<Post> findAllByBoardKindOrderByLikeCountDesc(BoardKind boardKind);   // 좋아요순
}