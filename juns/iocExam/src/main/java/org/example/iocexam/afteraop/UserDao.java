package org.example.iocexam.afteraop;

import org.springframework.stereotype.Component;

@Component
public class UserDao {
    public void addUser(){
        System.out.println("user를 추가하기위한 코드1");
        System.out.println("user를 추가하기위한 코드2");
        System.out.println("user를 추가하기위한 코드3");
        System.out.println("user를 추가하기위한 코드4");
        System.out.println("user를 추가하기위한 코드5");
    }

    public void updateUser(){
        System.out.println("user를 수정하기위한 코드1");
        System.out.println("user를 수정하기위한 코드2");
        System.out.println("user를 수정하기위한 코드3");
        System.out.println("user를 수정하기위한 코드4");
        System.out.println("user를 수정하기위한 코드5");
    }
}