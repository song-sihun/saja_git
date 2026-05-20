package org.example.iocexam.cookOrder.cook;

import org.springframework.stereotype.Component;

@Component
public class KoreanCook implements Cook {

    @Override
    public void cook() {
        System.out.println("비빔밥을 만듭니다.");
    }
}
