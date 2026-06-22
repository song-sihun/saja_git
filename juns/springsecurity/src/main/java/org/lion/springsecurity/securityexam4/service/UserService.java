package org.lion.springsecurity.securityexam4.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lion.springsecurity.securityexam4.domain.Role;
import org.lion.springsecurity.securityexam4.domain.User;
import org.lion.springsecurity.securityexam4.dto.UserRegisterDTO;
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
    public User createUser(UserRegisterDTO userRegisterDTO) {
        if(userRepository.findByUsername(userRegisterDTO.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Already Exists");
        }
        Role userRole = roleRepository.findByName("USER").orElse(null);
        Role adminRole = roleRepository.findByName("ADMIN").orElse(null);
        if (userRole == null) {
            Role role = new Role();
            role.setName("USER");
            userRole = roleRepository.save(role);
        }
        if (adminRole == null) {
            Role adminRole1 = new Role();
            adminRole1.setName("ADMIN");
            roleRepository.save(adminRole1);
        }
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);

        User user = new User();
        user.setUsername(userRegisterDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        user.setEmail(userRegisterDTO.getEmail());
        user.setName(userRegisterDTO.getName());
        user.setRoles(userRoles);
        return userRepository.save(user);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }


}
