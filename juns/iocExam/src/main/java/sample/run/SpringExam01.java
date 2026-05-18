package sample.run;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sample.bean.Book;
import sample.bean.Dice;
import sample.bean.Mybean;
import sample.config.MyBeanConfig;

public class SpringExam01 {
    public static void main(String[] args) {

//        // 직접
//        String name = "test";
//        int count = 10;
//        Mybean mybean = new Mybean();
//        mybean.setName(name);
//        System.out.println(mybean.getName());

        // 스프링 공장
        System.out.println("Before context");
        ApplicationContext context = new AnnotationConfigApplicationContext(MyBeanConfig.class);
        System.out.println("After context");

//
//        Mybean bean1 = context.getBean("mybean1", Mybean.class);
//        bean1.setName("test1");
//        System.out.println(bean1.getName());
//
//        Mybean bean2 = context.getBean("mybean2", Mybean.class);
//        bean2.setName("test2");
//        System.out.println(bean2.getName());
//
//        Mybean bean3 = context.getBean("mybean3", Mybean.class);
//        bean3.setName("test3");
//        System.out.println(bean3.getName());

        Book book = context.getBean("book", Book.class);
        System.out.println(book.getName());


        Dice dice = context.getBean("dice", Dice.class);
        System.out.println(dice.getNumber());

        Dice dice2 = context.getBean("dice2", Dice.class);
        System.out.println(dice2.getNumber());

        Dice dice3 = context.getBean("dice3", Dice.class);
        System.out.println(dice3.getNumber());


    }
}
