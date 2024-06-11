package com.example.ql_ban_do_an.Model;

public class Customer {
    int Id;
    String Address, Email, Name, Phone;

    public Customer() {
    }
    public Customer(int id, String address, String email, String name, String phone) {
        Id = id;
        Address = address;
        Email = email;
        Name = name;
        Phone = phone;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
