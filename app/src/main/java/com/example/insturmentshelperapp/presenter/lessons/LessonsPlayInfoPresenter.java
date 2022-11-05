package com.example.insturmentshelperapp.presenter.lessons;

import android.content.Context;

import com.example.insturmentshelperapp.interactor.ChordInteractor;
import com.example.insturmentshelperapp.interactor.LessonsInteractor;
import com.example.insturmentshelperapp.interactor.SharedPreferenceInteractor;
import com.example.insturmentshelperapp.model.ChordWithBitmap;
import com.example.insturmentshelperapp.model.LessonsIntensity;
import com.example.insturmentshelperapp.model.Lesson;
import com.example.insturmentshelperapp.presenter.Presenter;
import com.example.insturmentshelperapp.view.lessons.LessonsPlayInfoInterface;

import java.util.List;

public class LessonsPlayInfoPresenter implements Presenter {

    LessonsPlayInfoInterface view;
    LessonsInteractor lessonsInteractor;
    SharedPreferenceInteractor sharedPreferenceInteractor;
    ChordInteractor chordInteractor;

    public LessonsPlayInfoPresenter(LessonsPlayInfoInterface view, Context context){
        this.view = view;
        this.lessonsInteractor = new LessonsInteractor(context);
        this.sharedPreferenceInteractor = new SharedPreferenceInteractor(context);
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

    public void setTimeAndPoints(Lesson lesson){
        int intensity = sharedPreferenceInteractor.getSelectedLessonInstrumentIntensity(lesson.getInstrumentId());
        int time = LessonsIntensity.getPlayLessonTime(intensity);
        int points = LessonsIntensity.getPlayLessonPoints(intensity);
        double lessonIntensityMultipler;
        if(sharedPreferenceInteractor.getSelectedLessonInstrumentUseOfIntensity(lesson.getInstrumentId()) > 0 ){
            lessonIntensityMultipler = Double.parseDouble(lesson.getIntensity());
        }else {
            lessonIntensityMultipler = 1;
        }
        view.setTimeAndPoints(time, points, lessonIntensityMultipler);
    }

    public void setAdapter(Lesson lesson, float targetHeight){
        List<ChordWithBitmap> ChordsList = chordInteractor.getLessonChords(lesson, targetHeight);
        view.setChordsAdapter(ChordsList);
    }

    public void setContinueButton(Lesson lesson){
        view.setContinueButton(lesson);
    }
}
