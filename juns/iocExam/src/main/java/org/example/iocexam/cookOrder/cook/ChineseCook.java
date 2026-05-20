package org.example.iocexam.cookOrder.cook;
import org.springframework.stereotype.Component;

@Component
public class ChineseCook implements Cook {
    @Override
    public void cook() {
        System.out.println("짜장면을 만듭니다.");
    }
}
