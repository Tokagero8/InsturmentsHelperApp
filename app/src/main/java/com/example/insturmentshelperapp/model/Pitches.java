package com.example.insturmentshelperapp.model;

import java.util.ArrayList;
import java.util.List;

public class Pitches {
    public static Pitches pitches = null;
    public static List<Pitch> pitchesList;

    public static class Pitch {

        String name;
        double frequence;

        Pitch(String name, double frequence) {
            this.name = name;
            this.frequence = frequence;
        }
    }

    public static Pitches getInstance(){
        if(pitches == null){
            pitches = new Pitches();
        }
        return pitches;
    }

    public double findFrequenceFor(String name){
        for(Pitch pitch : pitchesList){
            if(pitch.name.equals(name)){
                return pitch.frequence;
            }
        }
        return -1;
    }

    Pitches(){
        List<Pitch> pitches = new ArrayList<>();

        pitches.add(new Pitch("C0", 16.35));
        pitches.add(new Pitch("C#0", 17.32));
        pitches.add(new Pitch("D0", 18.35));
        pitches.add(new Pitch("D#0", 19.45));
        pitches.add(new Pitch("E0", 20.60));
        pitches.add(new Pitch("F0", 21.83));
        pitches.add(new Pitch("F#0", 23.12));
        pitches.add(new Pitch("G0", 24.50));
        pitches.add(new Pitch("G#0", 25.96));
        pitches.add(new Pitch("A0", 27.50));
        pitches.add(new Pitch("A#0", 29.14));
        pitches.add(new Pitch("B0", 30.87));

        pitches.add(new Pitch("C1", 32.70));
        pitches.add(new Pitch("C#1", 34.65));
        pitches.add(new Pitch("D1", 36.71));
        pitches.add(new Pitch("D#1", 38.89));
        pitches.add(new Pitch("E1", 41.20));
        pitches.add(new Pitch("F1", 43.65));
        pitches.add(new Pitch("F#1", 46.25));
        pitches.add(new Pitch("G1", 49.00));
        pitches.add(new Pitch("G#1", 51.91));
        pitches.add(new Pitch("A1", 55.00));
        pitches.add(new Pitch("A#1", 58.27));
        pitches.add(new Pitch("B1", 61.74));

        pitches.add(new Pitch("C2", 65.41));
        pitches.add(new Pitch("C#2", 69.30));
        pitches.add(new Pitch("D2", 73.42));
        pitches.add(new Pitch("D#2", 77.78));
        pitches.add(new Pitch("E2", 82.41));
        pitches.add(new Pitch("F2", 87.31));
        pitches.add(new Pitch("F#2", 92.50));
        pitches.add(new Pitch("G2", 98.00));
        pitches.add(new Pitch("G#2", 103.83));
        pitches.add(new Pitch("A2", 110.00));
        pitches.add(new Pitch("A#2", 116.54));
        pitches.add(new Pitch("B2", 123.47));

        pitches.add(new Pitch("C3", 130.81));
        pitches.add(new Pitch("C#3", 138.59));
        pitches.add(new Pitch("D3", 146.83));
        pitches.add(new Pitch("D#3", 155.56));
        pitches.add(new Pitch("E3", 164.81));
        pitches.add(new Pitch("F3", 174.61));
        pitches.add(new Pitch("F#3", 185.00));
        pitches.add(new Pitch("G3", 196.00));
        pitches.add(new Pitch("G#3", 207.65));
        pitches.add(new Pitch("A3", 220.00));
        pitches.add(new Pitch("A#3", 233.08));
        pitches.add(new Pitch("B3", 246.94));

        pitches.add(new Pitch("C4", 261.63));
        pitches.add(new Pitch("C#4", 277.18));
        pitches.add(new Pitch("D4", 293.66));
        pitches.add(new Pitch("D#4", 311.13));
        pitches.add(new Pitch("E4", 329.63));
        pitches.add(new Pitch("F4", 349.23));
        pitches.add(new Pitch("F#4", 369.99));
        pitches.add(new Pitch("G4", 392.00));
        pitches.add(new Pitch("G#4", 415.30));
        pitches.add(new Pitch("A4", 440.00));
        pitches.add(new Pitch("A#4", 466.16));
        pitches.add(new Pitch("B4", 493.88));

        pitches.add(new Pitch("C5", 523.25));
        pitches.add(new Pitch("C#5", 554.37));
        pitches.add(new Pitch("D5", 587.33));
        pitches.add(new Pitch("D#5", 622.25));
        pitches.add(new Pitch("E5", 659.25));
        pitches.add(new Pitch("F5", 698.46));
        pitches.add(new Pitch("F#5", 739.99));
        pitches.add(new Pitch("G5", 783.99));
        pitches.add(new Pitch("G#5", 830.61));
        pitches.add(new Pitch("A5", 880.00));
        pitches.add(new Pitch("A#5", 932.33));
        pitches.add(new Pitch("B5", 987.77));

        pitches.add(new Pitch("C6", 1046.50));
        pitches.add(new Pitch("C#6", 1108.73));
        pitches.add(new Pitch("D6", 1174.66));
        pitches.add(new Pitch("D#6", 1244.51));
        pitches.add(new Pitch("E6", 1318.51));
        pitches.add(new Pitch("F6", 1396.91));
        pitches.add(new Pitch("F#6", 1479.98));
        pitches.add(new Pitch("G6", 1567.98));
        pitches.add(new Pitch("G#6", 1661.22));
        pitches.add(new Pitch("A6", 1760.00));
        pitches.add(new Pitch("A#6", 1864.66));
        pitches.add(new Pitch("B6", 1975.53));

        pitchesList = pitches;
    }
}