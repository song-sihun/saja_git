package org.lion.minirestapi.base.handler;

import org.springframework.web.bind.annotation.PathVariable;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
