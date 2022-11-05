package com.example.insturmentshelperapp.model;

public class LessonsIntensity {
    private static int[] playLessonTimeSeconds = {-1, 60, 180, 300, 600, 900};
    private static int[] playLessonPoints = {-1, 15, 30, 50, 100, 150};

    private static int[] chordLessonTimeSeconds = {-1, 60, 180, 300, 600, 900};
    private static int[] chordLessonPoints = {-1, 15, 30, 50, 100, 150};

    private static int[] nameLessonTimeSeconds = {-1, 60, 180, 300, 600, 900};
    private static int[] nameLessonPoints = {-1, 15, 30, 50, 100, 150};

    public static int getPlayLessonTime(int position){
        return playLessonTimeSeconds[position];
    }

    public static int getPlayLessonPoints(int position){
        return playLessonPoints[position];
    }

    public static int getChordLessonTime(int position){
        return chordLessonTimeSeconds[position];
    }

    public static int getChordLessonPoints(int position){
        return chordLessonPoints[position];
    }

    public static int getNameLessonTime(int position) {
        return nameLessonTimeSeconds[position];
    }

    public static int getNameLessonPoints(int position) {
        return nameLessonPoints[position];
    }


}
