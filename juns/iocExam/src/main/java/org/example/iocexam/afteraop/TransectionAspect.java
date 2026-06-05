package org.example.iocexam.afteraop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TransectionAspect {
    private static final Logger log = LoggerFactory.getLogger(TransectionAspect.class);

    @Before("execution(* org.example.iocexam.afteraop..*Service.*(..))")
    public void logging() {
        log.info("logging service??");
    }

    @Before("execution(* org.example.iocexam.afteraop.service.*.*(..))")
    public void beforeServiceMethod(JoinPoint joinPoint) {
        log.info("before service method {}", joinPoint.getSignature().getName());
    }

    @AfterReturning(
            pointcut = "execution(* org.example.iocexam.afteraop.service.*.*(..))",
            returning = "result"
    )
    public void afterReturningServiceMethod(JoinPoint joinPoint, Object result) {
        log.info("after service method {}", joinPoint.getSignature().getName());
        log.info("result {}", result);
    }


}
