package org.example.iocexam.afteraop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerAspect {
    private static final Logger log = LoggerFactory.getLogger(LoggerAspect.class);

    @Before("execution(* org.example.iocexam.afteraop.*Dao.*(..))")
    public void logging() {
        log.info("Logging started");
        log.info("Logging ok");
    }

}
