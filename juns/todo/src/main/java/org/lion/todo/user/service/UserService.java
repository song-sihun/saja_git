package org.lion.todo.user.service;

import lombok.RequiredArgsConstructor;
import org.lion.todo.config.exception.DuplicateUserException;
import org.lion.todo.config.exception.RoleNotFoundException;
import org.lion.todo.config.exception.UserNotFoundException;
import org.lion.todo.config.oauth.LoginType;
import org.lion.todo.user.domain.Role;
import org.lion.todo.user.domain.User;
import org.lion.todo.user.dto.UserRegisterDTO;
import org.lion.todo.user.dto.UserResponseDTO;
import org.lion.todo.user.repository.RoleRepository;
import org.lion.todo.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
        return UserResponseDTO.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }

    @Transactional
    public UserResponseDTO register(UserRegisterDTO userRegisterDTO) {

        if(userRepository.existsByEmail(userRegisterDTO.email())){
            throw new DuplicateUserException("Duplicated Email");
        }
        Role role = roleRepository.findByName("USER").orElseThrow(() -> new RoleNotFoundException("Role not found"));
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        User registerUser = User.builder()
                .email(userRegisterDTO.email())
                .username(userRegisterDTO.username())
                .roles(roles)
                .loginType(LoginType.LOCAL)
                .password(passwordEncoder.encode(userRegisterDTO.password()))
                .build();

        User savedUser = userRepository.save(registerUser);

        return UserResponseDTO.builder()
                .userId(savedUser.getId())
                .email(savedUser.getEmail())
                .username(savedUser.getUsername())
                .build();
    }


}
