package org.lion.springsecurity.basicjwt.service;

import lombok.RequiredArgsConstructor;
import org.lion.springsecurity.basicjwt.domain.RefreshToken;
import org.lion.springsecurity.basicjwt.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository ;

    @Transactional
    public RefreshToken saveRefreshToken(RefreshToken refreshToken){
        return refreshTokenRepository.save(refreshToken);
    }




}
