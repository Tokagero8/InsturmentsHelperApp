package com.example.insturmentshelperapp.view.lessons;

import com.example.insturmentshelperapp.model.Chord;

import java.util.List;

public interface LessonsNameInterface {
    void setNewTime(int time);
    void setChords(List<Chord> chordsList);
    void setNextChords(List<Chord> chordList);
    void addPoint();
    void runSummaryFragment();
    void disableButtons();
}
