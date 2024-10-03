package com.ott.onde.genre.entity;

import com.ott.onde.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*@Getter
@Entity
@NoArgsConstructor
@Table(name = "prefer_genre")
public class PreferGenre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prefer_id", updatable = false)
    private Long preferId;

    @Column(name = "genre_id", updatable = false)
    private String genreId;

    @ManyToOne
    @JoinColumn(name = "user_idx", nullable = false)
    private User user;
}*/
