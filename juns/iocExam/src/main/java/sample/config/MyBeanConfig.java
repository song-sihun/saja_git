package sample.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import sample.bean.Book;
import sample.bean.Dice;
import sample.bean.Game;
import sample.bean.Mybean;

@PropertySource("classpath:dice.properties")
public class MyBeanConfig {
    // 스프링 공장에게 나 어떤 빈을 관리할지 알려주는 기능

    @Bean
    public Mybean mybean1() {
        return new Mybean();
    }

    @Bean
    public Mybean mybean2() {
        return new Mybean();
    }

    @Bean
    @Scope("prototype")
    public Mybean mybean3() {
        return new Mybean();
    }

    @Bean
    public Book book() {
        return new Book("자바의 정석", "남궁성", "1234", 30000, 10);
    }
//
//    @Bean
//    public Book book2() {
//        return new Book();
//    }

    @Bean
    public Dice dice() {
        return new Dice(6);
    }

    @Bean
    public Dice dice2(@Value("${face}")int face) {
        return new Dice(face);
    }

    @Bean
    public Dice dice3() {
        Dice dice = new Dice();
        dice.setFace(666);
        return dice;
    }

}
