package com.example.insturmentshelperapp.presenter.lessons;

import android.content.Context;

import com.example.insturmentshelperapp.presenter.Presenter;
import com.example.insturmentshelperapp.view.lessons.LessonsInterface;

public class LessonsPresenter implements Presenter {
    LessonsInterface view;

    public LessonsPresenter(LessonsInterface view, Context context){
        this.view = view;
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

    public void runLesson(int position){
        switch(position){
            case 0:
                view.runPlayLesson();
                break;
            case 1:
                view.runChordLesson();
                break;
            case 2:
                view.runNameLesson();
                break;
            default:
                break;
        }
    }
}
