package com.example.insturmentshelperapp.view.lessons;

import com.example.insturmentshelperapp.model.Lesson;

public interface LessonsChordSummaryInterface {
    void setScore(String score);
    void setNeededScore(int neededScore, double lessonIntensityMultipler);
    void setResult(int score, int neededScore, double lessonIntensityMultipler);
    void setNextLesson(Lesson lesson, int repeatability);
    void disableButtons();
}
