package com.ott.onde.content.entity.user.genre;

import com.ott.onde.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "prefer_sentence")
public class PreferSentence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prefer_sentence_id")
    private Long PreferSentenceId;

    @Column(name = "prefer_sentence")
    private String preferSentence;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_code", nullable = false)
    private User user;
}
