package org.example.iocexam.annotation;

public class Person {
    private String name;
    private int age;

    public Person() {
        this.name = "John";
        this.age = 18;
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    private void sayHello() {
        System.out.println("Hello " + this.name);
    }
}
