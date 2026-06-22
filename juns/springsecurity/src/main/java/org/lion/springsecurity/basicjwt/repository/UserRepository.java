package org.lion.springsecurity.basicjwt.repository;

import org.lion.springsecurity.basicjwt.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
