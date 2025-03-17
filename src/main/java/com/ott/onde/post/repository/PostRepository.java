package com.ott.onde.post.repository;

import com.ott.onde.post.entity.BoardKind;
import com.ott.onde.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByBoardKind(BoardKind boardKind);
    List<Post> findAllByBoardKindOrderByPostViewsDesc(BoardKind boardKind);   // 조회순
    List<Post> findAllByBoardKindOrderByCreatedAtDesc(BoardKind boardKind); // 최신순
    List<Post> findAllByBoardKindOrderByLikeCountDesc(BoardKind boardKind);   // 좋아요순

    @Query(value = "select p.post_idx, p.user_code, p.title,p.contents, p.post_views," +
            "p.board_id, p.like_count, p.createdAt, p.modifiedAt from post as p, board as b " +
            "where b.parent_id = :parentId and p.board_id = b.board_id", nativeQuery = true)
    Page<Post> findTop3ByParentId(Pageable pageable, @Param("parentId")Long parentId);

    @Query(value = "select p.post_idx, p.user_code, p.title,p.contents, p.post_views," +
            "p.board_id, p.like_count, p.createdAt, p.modifiedAt from post as p, board as b " +
            "where b.board_id = :boardId and p.board_id = b.board_id", nativeQuery = true)
    Page<Post> findPostsByBoardId(Pageable pageable, @Param("boardId") Long boardId);
}