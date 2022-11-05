package com.example.insturmentshelperapp.model;

import java.io.Serializable;

public class Chord implements Serializable {
    int id;
    String tone;
    String type;
    String tabs;
    boolean isMain;
    int instrumentId;

    public Chord(){}

    public Chord(Chord chord){
        this.id = chord.id;
        this.tone = chord.tone;
        this.type = chord.type;
        this.tabs = chord.tabs;
        this.isMain = chord.isMain;
        this.instrumentId = chord.instrumentId;
    }

    public Chord(int id, String tone, String type, String tabs, int is_main, int instrumentId){
        this.id = id;
        this.tone = tone;
        this.type = type;
        this.tabs = tabs;
        this.isMain = is_main != 0;
        this.instrumentId = instrumentId;
    }

    public Chord(int id, String tone, String type, String tabs, boolean is_main, int instrumentId){
        this.id = id;
        this.tone = tone;
        this.type = type;
        this.tabs = tabs;
        this.isMain = is_main;
        this.instrumentId = instrumentId;
    }

    public int getId(){
        return id;
    }

    public String getTone(){
        return tone;
    }

    public String getType(){
        return type;
    }

    public String getTabs(){
        return tabs;
    }

    public boolean getIsMain(){
        return isMain;
    }

    public int getInstrumentId(){
        return instrumentId;
    }

    public void setInstrumentId(int instrumentId){
        this.instrumentId = instrumentId;
    }
}
