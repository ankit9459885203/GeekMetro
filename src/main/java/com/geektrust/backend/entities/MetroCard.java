package com.geektrust.backend.entities;



public class MetroCard {
    private final String id;
    private int balance;

    public MetroCard(String id, int balance) {
        this.id = id;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    public void deduct(int amount) {
        this.balance -= amount;
    }

    public void recharge(int amount) {
        this.balance += amount;
    }
}
