package com.example.easymapua;

public class Food {
    private String store, food, price;

    public Food(){

    }

    public Food(String store, String food, String price) {
        this.store = store;
        this.food = food;
        this.price = price;
    }

    public String getStore() {
        return store;
    }

    public String getFood() {
        return food;
    }

    public String getPrice() {
        return price;
    }
}
