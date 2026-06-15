package org.lion.springsecurity.securityexam4.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lion.springsecurity.securityexam4.domain.Role;
import org.lion.springsecurity.securityexam4.domain.User;
import org.lion.springsecurity.securityexam4.repository.RoleRepository;
import org.lion.springsecurity.securityexam4.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<Role> findRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    @Transactional
    public User createUser(User user) {
        Role userRole = roleRepository.findByName("USER").orElse(null);
        if (userRole == null) {
            Role role = new Role();
            role.setName("USER");
            userRole = roleRepository.save(role);
        }
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);
        user.setRoles(userRoles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }



}
