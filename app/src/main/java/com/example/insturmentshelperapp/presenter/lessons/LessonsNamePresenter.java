package com.example.insturmentshelperapp.presenter.lessons;

import android.content.Context;

import com.example.insturmentshelperapp.interactor.ChordInteractor;
import com.example.insturmentshelperapp.interactor.LessonsInteractor;
import com.example.insturmentshelperapp.interactor.SharedPreferenceInteractor;
import com.example.insturmentshelperapp.interactor.TimerInteractor;
import com.example.insturmentshelperapp.model.Chord;
import com.example.insturmentshelperapp.model.ChordWithBitmap;
import com.example.insturmentshelperapp.model.Lesson;
import com.example.insturmentshelperapp.model.LessonsIntensity;
import com.example.insturmentshelperapp.presenter.Presenter;
import com.example.insturmentshelperapp.view.lessons.LessonsNameInterface;

import java.util.List;

public class LessonsNamePresenter implements Presenter, LessonsPresenterTimerInterface {

    LessonsNameInterface view;
    SharedPreferenceInteractor sharedPreferenceInteractor;
    TimerInteractor timer;
    LessonsInteractor lessonsInteractor;
    ChordInteractor chordInteractor;

    public LessonsNamePresenter(LessonsNameInterface view, Context context){
        this.view = view;
        this.sharedPreferenceInteractor = new SharedPreferenceInteractor(context);
        this.timer = new TimerInteractor(this, context);
        this.lessonsInteractor = new LessonsInteractor(context);
        this.chordInteractor = new ChordInteractor(context);
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
        int time = LessonsIntensity.getNameLessonTime(intensity);
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

    public void setChords(Lesson lesson){
        List<Chord> chordsList = lessonsInteractor.getMultipleRandomChords(lesson, 4);
        view.setChords(chordsList);
    }


    public void setNextChords(Lesson lesson){
        List<Chord> chordsList = lessonsInteractor.getMultipleRandomChords(lesson, 4);
        view.setNextChords(chordsList);
    }

    public ChordWithBitmap getChordWithBitmap(Chord chord, float targetHeight){
        return chordInteractor.getLessonChordWithBitmap(chord, targetHeight);
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
