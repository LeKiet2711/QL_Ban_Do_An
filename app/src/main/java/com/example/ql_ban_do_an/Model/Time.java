package com.example.ql_ban_do_an.Model;

public class Time {
    private int Id;
    private String Value;
    public Time() {
    }

    public Time(int id) {
        Id = id;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }
}
