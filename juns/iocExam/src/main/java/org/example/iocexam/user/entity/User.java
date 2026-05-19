package org.example.iocexam.user.entity;

public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String nickname;
    private String email;
    private String password;
    private String role;
    private String phone;
    private String address;

    public  User() {
    }

    public User(int id, String firstName, String lastName, String nickname, String email, String password, String role, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }
}
