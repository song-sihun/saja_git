package org.example.springmvc.service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@NoArgsConstructor
public class UserService {

    public boolean login(String username, String password){
        if (username == null || password == null){
            return false;
        }
        log.info("username:{},password:{}",username,password);
        return username.equals("jun") && password.equals("1234");
    }

}
