package com.example.easymapua;

public class User {
    private String username, email, classification;

    public User(){

    }

    public User(String username, String email, String classification) {
        this.username = username;
        this.email = email;
        this.classification = classification;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getClassification() {
        return classification;
    }
}
