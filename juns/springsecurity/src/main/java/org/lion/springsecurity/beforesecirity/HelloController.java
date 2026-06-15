package org.lion.springsecurity.beforesecirity;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HelloController {
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/filter")
    public String filter(){
        log.info("FilterExam doFilter begin");
        return  "filter";
    }
}
