package org.lion.springdatajps;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    List<User> findUsersByNameLike(String name);
    List<User> findUsersByNameContaining(String name);

    @Transactional
    @Modifying
    @Query("DELETE FROM User u where u.email = :email")
    void deleteByEmail(@Param("email") String email);

    List<User> findUsersByNameLike(String name, Pageable pageable);
}
