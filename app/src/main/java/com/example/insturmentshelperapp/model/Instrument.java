package com.example.insturmentshelperapp.model;

public class Instrument {
    int id;
    String name;
    int stringsNumber;
    String image;
    String description;

    public Instrument(int id, String name, int stringsNumber, String image, String description){
        this.id = id;
        this.name = name;
        this.stringsNumber = stringsNumber;
        this.image = image;
        this.description = description;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public int getStringsNumber(){
        return stringsNumber;
    }

    public String getImage(){
        return image;
    }

    public String getDescription(){
        return description;
    }
}
