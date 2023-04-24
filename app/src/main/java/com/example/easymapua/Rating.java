package com.example.easymapua;

public class Rating {
    private String username, classification, store, subject, message, rate;

    public Rating(){

    }

    public Rating(String username, String classification, String store, String subject, String message, String rate) {
        this.username = username;
        this.classification = classification;
        this.store = store;
        this.subject = subject;
        this.message = message;
        this.rate = rate;
    }

    public String getUsername() {
        return username;
    }

    public String getClassification() {
        return classification;
    }

    public String getStore() {
        return store;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public String getRate() {
        return rate;
    }
}
