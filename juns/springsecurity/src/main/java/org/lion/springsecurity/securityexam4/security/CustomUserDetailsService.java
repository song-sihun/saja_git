package org.lion.springsecurity.securityexam4.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.lion.springsecurity.securityexam4.domain.Role;
import org.lion.springsecurity.securityexam4.domain.User;
import org.lion.springsecurity.securityexam4.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.security.core.userdetails.User.UserBuilder;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username + "not found"));

        UserBuilder userBuilder = org.springframework.security.core.userdetails.User.withUsername(user.getUsername());
        userBuilder.password(user.getPassword());

        userBuilder.roles(
                user.getRoles().stream().map(Role::getName).toArray(String[]::new)
        );

//
//        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
//                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName())).toList();
//
//        UserBuilder userBuilder = org.springframework.security.core.userdetails.User.withUsername(user.getUsername());
//        userBuilder.password(user.getPassword()).authorities(authorities);
//        userBuilder.authorities(authorities);

        return userBuilder.build();
    }
}
