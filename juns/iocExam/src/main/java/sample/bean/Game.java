package sample.bean;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Player> players;

    public Game() {
        System.out.println("Game constructor called");
    }

    public Game(List<Player> players) {
        this.players = players;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void play(){
        for (Player player : players) {
            player.play();
        }
    }
}
