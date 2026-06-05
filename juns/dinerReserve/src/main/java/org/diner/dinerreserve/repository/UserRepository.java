package org.diner.dinerreserve.repository;

import org.diner.dinerreserve.config.status.Role;
import org.diner.dinerreserve.config.status.UserStatus;
import org.diner.dinerreserve.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long>, PagingAndSortingRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber);

    List<User> findByPhoneNumberContaining(String phoneNumber);
    List<User> findByName(String name);
    List<User> findByStatus(UserStatus status);
    List<User> findByRole(Role role);

    void deleteByEmail(String email);
    Page<User> findAll(Pageable pageable);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
}
