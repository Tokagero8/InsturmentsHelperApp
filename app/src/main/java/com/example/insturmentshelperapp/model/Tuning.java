package com.example.insturmentshelperapp.model;

public class Tuning {
    int id;
    String name;
    String pitchArray;
    int instrumentId;

    public Tuning(int id, String name, String pitchArray, int instrumentId){
        this.id = id;
        this.name = name;
        this.pitchArray = pitchArray;
        this.instrumentId = instrumentId;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getPitchArray(){
        return pitchArray;
    }

    public int getInstrumentId(){
        return instrumentId;
    }

}
