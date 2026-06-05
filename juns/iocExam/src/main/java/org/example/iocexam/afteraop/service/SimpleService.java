package org.example.iocexam.afteraop.service;

import org.example.iocexam.afteraop.annotation.TrackTime;
import org.springframework.stereotype.Service;

@Service
public class SimpleService {
    @TrackTime
    public String simpleMethod() {
        return "jun";
    }
}
