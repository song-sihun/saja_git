package org.lion.minirestapi.auth.domain;

import jakarta.persistence.*;
import lombok.*;
import org.lion.minirestapi.base.domain.BaseTimeEntity;

@Entity
@Table(name = "refresh_tokens")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 1000)
    private String token;
    @Column(nullable = false, name = "user_id")
    private Long userId;

    public void updateToken(String token) {
        this.token = token;
    }
}
