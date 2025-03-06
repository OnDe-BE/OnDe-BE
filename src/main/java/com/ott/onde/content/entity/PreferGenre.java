package com.ott.onde.content.entity;

import com.ott.onde.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class PreferGenre {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long PreferGenreId;

    private String genre;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_code", nullable = false)
    private User user;
}
