package com.ott.onde.content.entity.user;


import com.ott.onde.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Data
@Table(name = "content_like")
public class ContentLike {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content_id")
    private String contentId;

    @ManyToOne
    @JoinColumn(name = "user_code")
    private User user;

    @UpdateTimestamp
    @Column(name="created_at", updatable = false)
    private LocalDateTime createdAt;
}
