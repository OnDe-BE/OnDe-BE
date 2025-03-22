package com.ott.onde.content.entity.genre;

import com.ott.onde.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "prefer_genre")
public class PreferGenre {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prefer_genre_id")
    private Long PreferGenreId;

    private String genre;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_code", nullable = false)
    private User user;
}
