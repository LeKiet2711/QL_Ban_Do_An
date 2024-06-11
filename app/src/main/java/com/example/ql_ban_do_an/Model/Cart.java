package com.example.ql_ban_do_an.Model;


import java.util.ArrayList;
import java.util.List;

public class Cart {
    private int id;
    private String email;
    private ArrayList<Foods> listFood;
    private boolean status;
    private String time;
    private double totalPrice;

    public Cart() {
    }

    public Cart(int id, String email, ArrayList<Foods> listFood, boolean status, String time, double totalPrice) {
        this.id = id;
        this.email = email;
        this.listFood = listFood;
        this.status = status;
        this.time = time;
        this.totalPrice = totalPrice;
    }

    // Các phương thức getter và setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Foods> getListFood() {
        return listFood;
    }

    public void setListFood(ArrayList<Foods> listFood) {
        this.listFood = listFood;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}

