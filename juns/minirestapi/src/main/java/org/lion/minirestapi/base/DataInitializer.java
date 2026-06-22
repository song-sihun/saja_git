package org.lion.minirestapi.base;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lion.minirestapi.user.domain.Role;
import org.lion.minirestapi.user.repository.RoleRepository;
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
                Role role = new Role();
                role.setName(roleName);
                roleRepository.save(role);
                log.info("Created role {}", role);
            }
        );
    }
}
