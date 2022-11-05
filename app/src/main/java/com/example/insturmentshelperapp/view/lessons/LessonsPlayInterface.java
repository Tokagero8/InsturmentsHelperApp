package com.example.insturmentshelperapp.view.lessons;

import com.example.insturmentshelperapp.model.Chord;
import com.example.insturmentshelperapp.model.ChordWithBitmap;

import java.util.List;

public interface LessonsPlayInterface {
    void setNewTime(int time);
    int getLessonTuningId();
    void setChord(ChordWithBitmap chordWithBitmap);
    Chord getSelectedChord();
    void setValidationResult(List<Integer> resultList);
    void setResultColors(List<Integer> colorList);
    void setValidationCircles(int stringNumber);
    boolean getIsChordChanging();
    void runSummaryFragment();
}
