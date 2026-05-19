package org.example.iocexam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IocExamApplication {

    public static void main(String[] args) {
        SpringApplication.run(org.example.iocexam.IocExamApplication.class, args);
    }

}

//    // 1. 변수를 변경 불가능하게 final로 선언 (권장)
//    private final UserController userController;
//    private final Dice dice;
//
//    // 2. 클래스 이름과 동일한 생성자를 만들고 매개변수로 주입받음 (@Autowired 생략 가능)
//    public IocExamApplication(UserController userController, Dice dice) {
//        this.userController = userController;
//        this.dice = dice;
//    }
//
//    public static void main(String[] args) {
//        SpringApplication.run(IocExamApplication.class, args);
//    }
//
//
//    @Override
//    public void run(String... args) throws Exception {
//        userController.joinUser();
//        System.out.println(dice.getNumber());
//
//    }
//}
