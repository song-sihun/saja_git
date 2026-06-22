package org.lion.minirestapi.user.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.lion.minirestapi.base.handler.UserNotFoundException;
import org.lion.minirestapi.user.domain.User;
import org.lion.minirestapi.user.dto.UserCreateDTO;
import org.lion.minirestapi.user.dto.UserResponseDTO;
import org.lion.minirestapi.user.dto.UserUpdateDTO;
import org.lion.minirestapi.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void create() {
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setUsername("username");
        userCreateDTO.setPassword("password");
        userCreateDTO.setEmail("email");
        userCreateDTO.setName("name");
        UserResponseDTO userResponseDTO = userService.create(userCreateDTO);
        assertNotNull(userResponseDTO);
    }

    @Test
    void findById() {
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setUsername("username");
        userCreateDTO.setPassword("password");
        userCreateDTO.setEmail("email");
        userCreateDTO.setName("name");
        UserResponseDTO userResponseDTO = userService.create(userCreateDTO);

        UserResponseDTO user = userService.findById(userResponseDTO.getId());
        assertNotNull(user);


    }

    @Test
    void findAll() {
    }

    @Test
    void updatePassword() {

        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setUsername("username");
        userCreateDTO.setPassword("password");
        userCreateDTO.setEmail("email");
        userCreateDTO.setName("name");
        UserResponseDTO userResponseDTO = userService.create(userCreateDTO);

        UserResponseDTO user = userService.findById(userResponseDTO.getId());
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setCurrentPassword("password");
        userUpdateDTO.setNewPassword("newPassword");
        UserResponseDTO updatedUser = userService.updatePassword(user.getId(), userUpdateDTO);
        assertNotNull(updatedUser);

    }

    @Test
    void deleteById() {
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setUsername("username");
        userCreateDTO.setPassword("password");
        userCreateDTO.setEmail("email");
        userCreateDTO.setName("name");
        UserResponseDTO userResponseDTO = userService.create(userCreateDTO);

        User loginUser = userRepository.findById(userResponseDTO.getId()).orElseThrow(() -> new UserNotFoundException("User not found"));
        userService.deleteById(userResponseDTO.getId(), loginUser);

    }

    @Test
    void deleteById_otherUser_forbidden() {
        UserResponseDTO targetUser = userService.create(createUserCreateDTO("target"));
        UserResponseDTO otherUser = userService.create(createUserCreateDTO("other"));
        User loginUser = userRepository.findById(otherUser.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        assertThrows(AccessDeniedException.class, () -> {
            userService.deleteById(targetUser.getId(), loginUser);
        });
    }

    private UserCreateDTO createUserCreateDTO(String prefix) {
        String unique = prefix + "-" + UUID.randomUUID().toString().substring(0, 8);
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setUsername(unique);
        userCreateDTO.setPassword("password");
        userCreateDTO.setEmail(unique + "@test.com");
        userCreateDTO.setName(prefix);
        return userCreateDTO;
    }



    @Test
    void create_encryptsPasswordAndAssignsUserRole() {
        UserCreateDTO dto = createUserCreateDTO("member");
        String rawPassword = dto.getPassword();

        UserResponseDTO result = userService.create(dto);

        User saved = userRepository.findById(result.getId())
                .orElseThrow();

        assertTrue(passwordEncoder.matches(rawPassword, saved.getPassword()));

        assertTrue(saved.getRoles().stream()
                .anyMatch(role -> role.getName().equals("USER")));
    }
}
