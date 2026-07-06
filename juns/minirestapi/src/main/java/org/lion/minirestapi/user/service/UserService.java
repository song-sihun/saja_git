package org.lion.minirestapi.user.service;

import lombok.RequiredArgsConstructor;
import org.lion.minirestapi.base.handler.DuplicateUserException;
import org.lion.minirestapi.base.handler.ErrorString;
import org.lion.minirestapi.base.handler.RoleNotFoundException;
import org.lion.minirestapi.base.handler.UserNotFoundException;
import org.lion.minirestapi.user.domain.Role;
import org.lion.minirestapi.user.domain.User;
import org.lion.minirestapi.user.dto.UserCreateDTO;
import org.lion.minirestapi.user.dto.UserResponseDTO;
import org.lion.minirestapi.user.dto.UserUpdateDTO;
import org.lion.minirestapi.user.repository.RoleRepository;
import org.lion.minirestapi.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDTO create(UserCreateDTO userCreateDTO) {

        if (userRepository.existsByEmail(userCreateDTO.getEmail())) {
            throw new DuplicateUserException(ErrorString.DUPLICATED.getMessage());
        }

        if (userRepository.existsByUsername(userCreateDTO.getUsername())) {
            throw new DuplicateUserException(ErrorString.DUPLICATED.getMessage());
        }

        Role role = roleRepository.findByName("USER").orElseThrow(() -> new RoleNotFoundException(ErrorString.ROLE_NOT_FOUND.getMessage()));
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        userCreateDTO.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
        User user = userRepository.save(User.fromDTO(userCreateDTO, roles));

        return UserResponseDTO.fromEntity(user);
    }


    public UserResponseDTO findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(ErrorString.USER_NOT_FOUND.getMessage()));
        return UserResponseDTO.fromEntity(user);
    }

    public UserResponseDTO findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(ErrorString.USER_NOT_FOUND.getMessage()));
        return UserResponseDTO.fromEntity(user);
    }

    public User findUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(ErrorString.USER_NOT_FOUND.getMessage()));
    }

    public Page<UserResponseDTO> findAll(User loginUser, Pageable pageable) {
        if(loginUser.getRoles().stream().noneMatch(role -> role.getName().equals("ADMIN"))) {
            throw new AccessDeniedException(ErrorString.ACCESS_DENIED.getMessage());
        }
        return userRepository.findAll(pageable).map(UserResponseDTO::fromEntity);
    }

    @Transactional
    public UserResponseDTO updatePassword(Long id, UserUpdateDTO userUpdateDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(ErrorString.USER_NOT_FOUND.getMessage()));
        if (!passwordEncoder.matches(userUpdateDTO.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
        }
        user.updatePassword(passwordEncoder.encode(userUpdateDTO.getNewPassword()));
        return UserResponseDTO.fromEntity(user);
    }

    @Transactional
    public void deleteById(Long id, User loginUser) {
        boolean isOwner = loginUser.getId().equals(id);
        boolean isAdmin = loginUser.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ADMIN"));
        if (!isOwner && !isAdmin) {
            throw new AccessDeniedException(ErrorString.ACCESS_DENIED.getMessage());
        }
        userRepository.deleteById(id);
    }


    public UserResponseDTO findByProviderAndSocialId(String provider, String socialId) {
        User user = userRepository.findByProviderAndSocialId(provider, socialId).orElseThrow(() -> new UserNotFoundException(ErrorString.USER_NOT_FOUND.getMessage()));
        return UserResponseDTO.fromEntity(user);
    }

}
