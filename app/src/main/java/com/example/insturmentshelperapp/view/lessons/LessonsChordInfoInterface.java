package com.example.insturmentshelperapp.view.lessons;

import com.example.insturmentshelperapp.model.ChordWithBitmap;
import com.example.insturmentshelperapp.model.Lesson;

import java.util.List;

public interface LessonsChordInfoInterface {
    void setTimeAndPoints(int time, int points, double multipler);
    void setChordsAdapter(List<ChordWithBitmap> chords);
    void setContinueButton(Lesson lesson);
}
