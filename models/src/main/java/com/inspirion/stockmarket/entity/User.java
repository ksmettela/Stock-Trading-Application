package com.inspirion.stockmarket.entity;

public class User extends com.inspirion.stockmarket.model.User {
    public User(long id, String firstName, String lastName, String email, String password, double phoneNumber, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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

    public double getPhoneNumber() {
        return phoneNumber;
    }
    long id;
}
