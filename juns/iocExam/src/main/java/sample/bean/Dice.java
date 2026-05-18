package sample.bean;

public class Dice {

    private int face = 6;

    public Dice(){
    }

    public Dice(int face) {
        this.face = face;
    }

    public int getFace() {
        return face;
    }
    public void setFace(int face) {
        this.face = face;
    }

    public int getNumber() {
        return (int)(Math.random()*face)+1;
    }
}
