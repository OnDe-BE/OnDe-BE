package com.ott.onde.post.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private int boardId;

    @Column(name = "board_name", nullable = false)
    private String boardName;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Post> postList = new ArrayList<>();

    /**
     * parent_id를 같은 테이블의 board_id로 참조
     * - 부모가 없는 최상위 게시판일 경우 parent = null
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Board parent;


}