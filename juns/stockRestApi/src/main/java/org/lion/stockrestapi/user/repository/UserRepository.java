package org.lion.stockrestapi.user.repository;

import org.lion.stockrestapi.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
}
