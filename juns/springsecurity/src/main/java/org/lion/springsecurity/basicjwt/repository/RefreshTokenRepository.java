package org.lion.springsecurity.basicjwt.repository;

import org.lion.springsecurity.basicjwt.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}
