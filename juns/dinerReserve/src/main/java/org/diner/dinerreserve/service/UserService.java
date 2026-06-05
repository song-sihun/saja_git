package org.diner.dinerreserve.service;

import lombok.RequiredArgsConstructor;
import org.diner.dinerreserve.config.exception.CustomException;
import org.diner.dinerreserve.config.status.Role;
import org.diner.dinerreserve.config.status.UserStatus;
import org.diner.dinerreserve.dto.user.SessionUser;
import org.diner.dinerreserve.domain.User;
import org.diner.dinerreserve.dto.user.LoginRequest;
import org.diner.dinerreserve.dto.user.PasswordChangeRequest;
import org.diner.dinerreserve.dto.user.UserCreateRequest;
import org.diner.dinerreserve.dto.user.UserUpdateRequest;
import org.diner.dinerreserve.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SessionUser login(LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException("존재하지 않는 이메일입니다."));
        if(!user.getStatus().equals(UserStatus.ACTIVE)){
            throw new CustomException("로그인 할 수 없습니다.");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException("비밀번호가 일치하지 않습니다.");
        }


        return new SessionUser(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }

    @Transactional
    public void delete(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        user.delete();
        userRepository.save(user);

    }

    @Transactional
    public void block(User user) {
        user.block();
    }

    @Transactional
    public void unblock(User user) {
        user.unblock();
    }

    @Transactional
    public void deactivate(User user) {
        user.deactivate();
    }

    @Transactional
    public SessionUser update(UserUpdateRequest userUpdateRequest, SessionUser sessionUser) {
        User updatedUser = userRepository.findByEmail(sessionUser.getEmail())
                .orElseThrow(() -> new CustomException("존재하지 않는 이메일입니다."));
        updatedUser.setName(userUpdateRequest.getName());
        updatedUser.setPhoneNumber(userUpdateRequest.getPhoneNumber().replace("-", ""));
        updatedUser.setUpdatedAt(LocalDateTime.now());
        userRepository.save(updatedUser);

        return new SessionUser(
                updatedUser.getId(),
                updatedUser.getName(),
                updatedUser.getEmail(),
                updatedUser.getRole()
        );
    }

    @Transactional
    public void join(UserCreateRequest request){
        LocalDateTime now = LocalDateTime.now();

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new CustomException("User with email already exists!");
        }
        if(!request.getConfirmPassword().equals(request.getPassword())) {
            throw new CustomException("Passwords do not match!");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User newUser = new User();
        newUser.setName(request.getName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(encodedPassword);
        newUser.setPhoneNumber(request.getPhoneNumber().replace("-", ""));
        newUser.setRole(Role.USER);
        newUser.setStatus(UserStatus.ACTIVE);
        newUser.setCreatedAt(now);
        newUser.setUpdatedAt(now);
        userRepository.save(newUser);
    }


    @Transactional
    public void changePassword(Long Id, PasswordChangeRequest request) {
        User user = userRepository.findById(Id)
                .orElseThrow(() -> new CustomException("존재하지 않는 사용자입니다."));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new CustomException("현재 비밀번호가 일치하지 않습니다.");
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new CustomException("새 비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new CustomException("새 비밀번호는 현재 비밀번호와 달라야 합니다.");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

}
