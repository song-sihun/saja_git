package sample.bean;


public class Mybean {
    private String name;
    private int count;

    public Mybean() {
        System.out.println("Mybean constructor called");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Mybean{" +
                "name='" + name + '\'' +
                ", count=" + count +
                '}';
    }
}
