package com.example.insturmentshelperapp.presenter.lessons;

import android.content.Context;

import com.example.insturmentshelperapp.interactor.LessonsInteractor;
import com.example.insturmentshelperapp.interactor.SharedPreferenceInteractor;
import com.example.insturmentshelperapp.interactor.TimerInteractor;
import com.example.insturmentshelperapp.model.ChordWithBitmap;
import com.example.insturmentshelperapp.model.Lesson;
import com.example.insturmentshelperapp.model.LessonsIntensity;
import com.example.insturmentshelperapp.presenter.Presenter;
import com.example.insturmentshelperapp.view.lessons.LessonsChordInterface;

import java.util.List;

public class LessonsChordPresenter implements Presenter, LessonsPresenterTimerInterface {

    LessonsChordInterface view;
    SharedPreferenceInteractor sharedPreferenceInteractor;
    TimerInteractor timer;
    LessonsInteractor lessonsInteractor;

    public LessonsChordPresenter(LessonsChordInterface view, Context context){
        this.view = view;
        this.sharedPreferenceInteractor = new SharedPreferenceInteractor(context);
        this.timer = new TimerInteractor(this, context);
        this.lessonsInteractor = new LessonsInteractor(context);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void setNewTime(int time) {
        view.setNewTime(time);
    }

    @Override
    public void finishLesson() {
        view.runSummaryFragment();
    }

    public void setTimer(Lesson lesson){
        int intensity = sharedPreferenceInteractor.getSelectedLessonInstrumentIntensity(lesson.getInstrumentId());
        int time = LessonsIntensity.getChordLessonTime(intensity);
        double lessonIntensityMultipler;
        if(sharedPreferenceInteractor.getSelectedLessonInstrumentUseOfIntensity(lesson.getInstrumentId()) > 0 ){
            lessonIntensityMultipler = Double.parseDouble(lesson.getIntensity());
        }else {
            lessonIntensityMultipler = 1;
        }
        if(time == -1){
            view.setNewTime(0);
        }else{
            int lessonTime = (int) (time * lessonIntensityMultipler);
            view.setNewTime(lessonTime);
            timer.setTimer(lessonTime);
        }
    }

    public void setChords(Lesson lesson, float targetHeight){
        List<ChordWithBitmap> chordsWithBitmapList = lessonsInteractor.getMultipleRandomChordsWithBitmap(lesson, targetHeight, 4);
        view.setChords(chordsWithBitmapList);
    }

    public void setNextChords(Lesson lesson, float targetHeight){
        List<ChordWithBitmap> chordsWithBitmapList = lessonsInteractor.getMultipleRandomChordsWithBitmap(lesson, targetHeight, 4);
        view.setNextChords(chordsWithBitmapList);
    }

    public void addPoint(){
        view.addPoint();
    }

    public void disableButtons(){
        view.disableButtons();
    }

    public void startTimer(){
        timer.startTimer();
    }

    public void stopTimer(){
        timer.stopTimer();
    }


}
