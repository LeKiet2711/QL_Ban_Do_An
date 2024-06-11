package com.example.ql_ban_do_an.Model;

public class Price {
    private int Id;
    private String Value;

    public Price() {
    }

    public Price(int id) {
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

    @Override
    public String toString() {
        return Value;
    }
}
