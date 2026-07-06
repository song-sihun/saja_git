package org.lion.minirestapi.oauth.service;

import lombok.RequiredArgsConstructor;
import org.lion.minirestapi.base.handler.UserNotFoundException;
import org.lion.minirestapi.oauth.config.LoginType;
import org.lion.minirestapi.oauth.domain.SocialLoginInfo;
import org.lion.minirestapi.oauth.repository.SocialLoginInfoRepository;
import org.lion.minirestapi.user.domain.Role;
import org.lion.minirestapi.user.domain.User;
import org.lion.minirestapi.user.repository.RoleRepository;
import org.lion.minirestapi.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SocialLoginInfoService {
    private final SocialLoginInfoRepository socialLoginInfoRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SocialLoginInfo saveOrUpdateUser(String socialId, String provider,
                                            String username, String email, String avatarUrl) {

        Optional<SocialLoginInfo> existingSocialLoginInfo =
                socialLoginInfoRepository.findByProviderAndSocialId(provider, socialId);

        if (existingSocialLoginInfo.isPresent()) {
            return existingSocialLoginInfo.get();
        }

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> createSocialUser(socialId, provider, username, email, avatarUrl));

        SocialLoginInfo socialLoginInfo = SocialLoginInfo.builder()
                .socialId(socialId)
                .provider(provider)
                .avatarUrl(avatarUrl)
                .user(user)
                .build();

        return socialLoginInfoRepository.save(socialLoginInfo);
    }

    private User createSocialUser(String socialId, String provider, String username, String email, String avatarUrl ) {
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalArgumentException("기본 권한을 찾을 수 없습니다."));

        return userRepository.save(
                User.builder()
                        .username(provider + "_" + socialId)
                        .email(email)
                        .name(username)
                        .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                        .loginType(LoginType.SOCIAL)
                        .provider(provider)
                        .socialId(socialId)
                        .avatarUrl(avatarUrl)
                        .roles(Set.of(userRole))
                        .build()
        );
    }
}
