package com.ott.onde.content.entity.util;


import com.ott.onde.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "content_link_view")
public class ContentLinkView {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content_id")
    private String contentId;

    @Column(name = "platform")
    private String platform;

    @Column(name = "user_code")
    private String userCode;

    @Builder
    public ContentLinkView(String contentId, String platform, String userCode) {
        this.contentId = contentId;
        this.platform = platform;
        this.userCode = userCode;
    }
}
