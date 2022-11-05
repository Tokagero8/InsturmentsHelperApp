package com.example.insturmentshelperapp.presenter.lessons;

import com.example.insturmentshelperapp.model.Chord;

import java.util.List;

public interface LessonsPlayPresenterInterface {
    //void setNewTime(int time);
    int getLessonTuningId();
    Chord getSelectedChord();
    void setValidationResult(List<Integer> resultList);
    boolean getIsChordChanging();
   //void finishLesson();
}
