package com.ott.onde.post.repository;

import com.ott.onde.post.entity.BoardKind;
import com.ott.onde.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardKindRepository extends JpaRepository<BoardKind, Long> {
    List<BoardKind> findAllByBoardid(Integer boardId);
}
