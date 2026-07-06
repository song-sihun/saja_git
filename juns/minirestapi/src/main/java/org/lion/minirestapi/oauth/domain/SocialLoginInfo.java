package org.lion.minirestapi.oauth.domain;

import jakarta.persistence.*;
import lombok.*;
import org.lion.minirestapi.user.domain.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "social_login_info",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"provider", "social_id"})
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SocialLoginInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String provider;

    @Column(name = "social_id", nullable = false)
    private String socialId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String avatarUrl;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}