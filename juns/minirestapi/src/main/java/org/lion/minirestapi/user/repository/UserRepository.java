package org.lion.minirestapi.user.repository;

import org.lion.minirestapi.user.domain.Role;
import org.lion.minirestapi.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    List<User> findByRoles(Set<Role> roles);
    Page<User> findAll(Pageable pageable);
    boolean existsByEmail(String email);

    Optional<User> findByProviderAndSocialId(String provider, String socialId);

    Optional<User> findByUsernameAndEmail(String username, String email);

    Optional<User> findByEmail(String email);
}
