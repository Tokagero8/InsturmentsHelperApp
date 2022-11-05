package com.example.insturmentshelperapp.presenter.lessons;

import android.content.Context;

import com.example.insturmentshelperapp.interactor.LessonsInteractor;
import com.example.insturmentshelperapp.model.Lesson;
import com.example.insturmentshelperapp.presenter.Presenter;
import com.example.insturmentshelperapp.view.lessons.LessonsPlayPresentationInterface;

import java.util.List;

public class LessonsPlayPresentationPresenter implements Presenter {
    LessonsPlayPresentationInterface view;
    LessonsInteractor lessonsInteractor;

    public LessonsPlayPresentationPresenter(LessonsPlayPresentationInterface view, Context context){
        this.view = view;
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

    public List<Lesson> getLessons(){
        return lessonsInteractor.getLessonsWithType(1);
    }

    public void runLesson(Object lesson){
        view.runLesson((Lesson)lesson);
    }
}
