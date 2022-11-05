package com.example.insturmentshelperapp.view.lessons;

import com.example.insturmentshelperapp.model.ChordWithBitmap;

import java.util.List;

public interface LessonsChordInterface {
    void setNewTime(int time);
    void setChords(List<ChordWithBitmap> chordsWithBitmapList);
    void setNextChords(List<ChordWithBitmap> chordsWithBitmapList);
    void addPoint();
    void runSummaryFragment();
    void disableButtons();
}
