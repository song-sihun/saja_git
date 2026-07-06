package org.lion.minirestapi.oauth.repository;


import org.lion.minirestapi.oauth.domain.SocialLoginInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SocialLoginInfoRepository extends JpaRepository<SocialLoginInfo, Long> {
    Optional<SocialLoginInfo> findByProviderAndSocialId(String provider, String socialId);

    @Query("""
            select s
            from SocialLoginInfo s
            join fetch s.user
            where s.provider = :provider
              and s.socialId = :socialId
            """)
    Optional<SocialLoginInfo> findByProviderAndSocialIdWithUser(
            @Param("provider") String provider,
            @Param("socialId") String socialId
    );
}
