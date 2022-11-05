package com.example.insturmentshelperapp.model;

public class TonesNames {
    private String[] tonesNames = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "B", "H"};

    public String[] getTonesNames(){
        return tonesNames;
    }
    public String getToneName(int position){
        return tonesNames[position];
    }
}
