package com.example.insturmentshelperapp.presenter.lessons;

import android.content.Context;

import com.example.insturmentshelperapp.interactor.LessonsInteractor;
import com.example.insturmentshelperapp.model.Lesson;
import com.example.insturmentshelperapp.presenter.Presenter;
import com.example.insturmentshelperapp.view.lessons.LessonsNamePresentationInterface;

import java.util.List;

public class LessonsNamePresentationPresenter implements Presenter {

    LessonsNamePresentationInterface view;
    LessonsInteractor lessonsInteractor;

    public LessonsNamePresentationPresenter(LessonsNamePresentationInterface view, Context context){
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
        return lessonsInteractor.getLessonsWithType(3);
    }

    public void runLesson(Object lesson){
        view.runLesson((Lesson)lesson);
    }
}
