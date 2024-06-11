package com.example.ql_ban_do_an.Model;

public class Category {
    private int Id;
    private String ImagePath;
    private String Name;

    public Category(int id, String imagePath, String name) {
        Id = id;
        ImagePath = imagePath;
        Name = name;
    }

    public Category() {

    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

}
