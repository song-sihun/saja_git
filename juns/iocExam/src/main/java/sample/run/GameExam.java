package sample.run;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sample.bean.Dice;
import sample.bean.Game;
import sample.bean.Player;
import sample.config.GameConfig;

import java.util.ArrayList;

public class GameExam {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(GameConfig.class);
        Game game = context.getBean("game2", Game.class);
        game.play();
    }
}
