package org.lion.swaggerexam.service;

import lombok.RequiredArgsConstructor;
import org.lion.swaggerexam.entity.User;
import org.lion.swaggerexam.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private UserRepository userRepository;

    // signup
    @Transactional
    public User registerUser(String email, String password) {
        if(userRepository.findByEmail(email).isPresent()) {
            return null;
        }

        User newUser = User.builder()
                .email(email)
                .password(password)
                .build();

        userRepository.save(newUser);
        return newUser;
    }
}
