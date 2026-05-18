package sample.bean;

public class Player {
    private String name;
    private Dice dice; // 실행될 때 주입

    public Player() {

    }

    public Player(String name, Dice dice) {
        this.name = name;
        this.dice = dice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Dice getDice() {
        return dice;
    }

    public void setDice(Dice dice) {
        this.dice = dice;
    }

    public void play() {
        System.out.println(name + " gets " + dice.getNumber());
    }
}
