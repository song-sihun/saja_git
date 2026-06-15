package org.lion.springsecurity.securityexam4.repository;

import org.lion.springsecurity.securityexam4.domain.Role;
import org.lion.springsecurity.securityexam4.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    List<User> findByRoles(Set<Role> roles);
    boolean existsByUsername(String username);
}
