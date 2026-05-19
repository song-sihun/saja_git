package org.example.iocexam.cookOrder.cook;

public class JapaneseCook implements Cook{
    @Override
    public void cook() {
        System.out.println("초밥을 만듭니다.");
    }
}
