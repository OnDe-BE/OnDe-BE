package com.ott.onde.post.entity;

import com.ott.onde.post.entity.Likes;
import com.ott.onde.post.entity.Post;
import com.ott.onde.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "board_kind")
public class BoardKind {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boardid")
    private int boardId;

    @Column(name = "board_name", nullable = false)
    private String boardName;

    @OneToMany(mappedBy = "boardKind", cascade = CascadeType.REMOVE)
    private List<Post> postList = new ArrayList<>();


}