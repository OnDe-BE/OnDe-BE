package com.ott.onde.content.entity;

import com.ott.onde.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class PreferSentence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long PreferSentenceId;

    private String preferSentence;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_code", nullable = false)
    private User user;
}
