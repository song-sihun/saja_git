package org.example.iocexam.afteraop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AopService {

    private static final Logger log = LoggerFactory.getLogger(AopService.class);

    public void testService(){
        log.info("testService");
    }
}
