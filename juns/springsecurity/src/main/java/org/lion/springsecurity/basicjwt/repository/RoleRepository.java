package org.lion.springsecurity.basicjwt.repository;

import org.lion.springsecurity.basicjwt.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
