package org.lion.springsecurity.beforesecirity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    public void threadLocalTest(){
        log.info("User name : {}",UserContext.getUser().getName());
    }
}
