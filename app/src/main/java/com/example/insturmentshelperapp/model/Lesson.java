package com.example.insturmentshelperapp.model;

import java.io.Serializable;

public class Lesson implements Serializable {
    int id;
    String name;
    String chordsIds;
    String difficulty;
    String progress;
    String bestScore;
    String intensity;
    int instrumentId;
    int lessonTuningId;
    int lessonType;

    public Lesson(int id, String name, String chordsIds, String difficulty, String progress, String bestScore, String intensity, int instrumentId, int lessonTuningId, int lessonType){
        this.id = id;
        this.name = name;
        this.chordsIds = chordsIds;
        this.difficulty = difficulty;
        this.progress = progress;
        this.bestScore = bestScore;
        this.intensity = intensity;
        this.instrumentId = instrumentId;
        this.lessonTuningId = lessonTuningId;
        this.lessonType = lessonType;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getChords() {
        return chordsIds;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getProgress() {
        return progress;
    }

    public String getBestScore() {
        return bestScore;
    }

    public String getIntensity() {return intensity;}

    public int getInstrumentId() {return instrumentId;}

    public int getLessonTuningId() {return lessonTuningId;}

    public int getLessonType() {return lessonType;}


}
