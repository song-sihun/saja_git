package sample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import sample.bean.Dice;
import sample.bean.Game;
import sample.bean.Player;

import java.util.List;

public class GameConfig {

    //Dice
    @Bean
    public Dice dice(){
        return new Dice(6);
    }

    //Player
    @Bean
    public Player player1(Dice dice){
        Player player = new Player();
        player.setName("player1");
        player.setDice(dice);
        return player;
    }

    @Bean
    public Player player2(Dice dice){
        Player player = new Player();
        player.setName("player2");
        player.setDice(dice);
        return player;
    }

    @Bean
    public Player player3(Dice dice){
        return new Player("player3_with_constructor", dice);
    }

    //Game
    @Bean
    public Game game(List<Player> players){
        Game game = new Game();
        game.setPlayers(players);
        return game;
    }

    //Game
    @Bean
    public Game game2(List<Player> players){
        return new Game(players);
    }
}
