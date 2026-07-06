package org.lion.todo.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lion.todo.user.domain.Role;
import org.lion.todo.user.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        createRole("USER");
        createRole("ADMIN");
    }

    public void createRole(String roleName) {
        roleRepository.findByName(roleName).ifPresentOrElse(
                role -> {
                    log.info("Already exists role with name {}", roleName);
                },
                () -> {
                    Role role = Role.builder().name(roleName).build();
                    roleRepository.save(role);
                    log.info("Created role {}", role);
                }
        );
    }
}
