package org.lion.stockrestapi.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lion.stockrestapi.user.domain.Role;
import org.lion.stockrestapi.user.domain.User;
import org.lion.stockrestapi.user.dto.UserCreateDTO;
import org.lion.stockrestapi.user.dto.UserResponseDTO;
import org.lion.stockrestapi.user.dto.UserUpdateDTO;
import org.lion.stockrestapi.user.repository.RoleRepository;
import org.lion.stockrestapi.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public Page<UserResponseDTO> findAll(User loginUser, Pageable pageable) {
        if(loginUser.getRoles().stream().noneMatch(role -> role.getName().equals("ADMIN"))) {
            log.info("Access denied");
        }

        Page<User> users = userRepository.findAll(pageable);
        return users.map(UserResponseDTO::fromEntity);
    }

    public UserResponseDTO createUser(UserCreateDTO userCreateDTO) {
        userCreateDTO.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
        Role role = roleRepository.findByName("USER").orElseThrow(() -> new RuntimeException("Role not found"));
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        User user = userRepository.save(User.fromDTO(userCreateDTO, roles));
        return UserResponseDTO.fromEntity(user);
    }

    public UserResponseDTO updateUserPassword(Long userId, UserUpdateDTO userUpdateDTO, User loginUser) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if(!user.getId().equals(loginUser.getId())) {
            throw new AccessDeniedException("Access denied");
        }
        if(!passwordEncoder.matches(user.getPassword(), userUpdateDTO.getCurrentPassword())) {
            throw new RuntimeException("Passwords don't match");
        }
        user.updatePassword(passwordEncoder.encode(userUpdateDTO.getNewPassword()));
        return UserResponseDTO.fromEntity(user);
    }


    public void deleteUser(Long id, User loginUser) {
        User user =  userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        boolean isOwner = user.getId().equals(loginUser.getId());
        boolean isAdmin = loginUser.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"));
        if(!isOwner && !isAdmin) {
            throw new RuntimeException("User is not owner of this role");
        }
        userRepository.delete(user);
    }
}
