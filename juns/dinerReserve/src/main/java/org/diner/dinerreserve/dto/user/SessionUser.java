package org.diner.dinerreserve.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.diner.dinerreserve.config.status.Role;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SessionUser {
    private Long id;
    private String email;
    private String name;
    private Role role;
}
