package com.example.insturmentshelperapp.presenter.lessons;

import android.content.Context;

import com.example.insturmentshelperapp.interactor.LessonsInteractor;
import com.example.insturmentshelperapp.interactor.SharedPreferenceInteractor;
import com.example.insturmentshelperapp.model.Lesson;
import com.example.insturmentshelperapp.presenter.Presenter;
import com.example.insturmentshelperapp.view.lessons.LessonsSettingsSelectLessonsInterface;

import java.util.List;

public class LessonsSettingsSelectLessonsPresenter implements Presenter {

    LessonsSettingsSelectLessonsInterface view;
    LessonsInteractor lessonsInteractor;
    SharedPreferenceInteractor sharedPreferenceInteractor;

    public LessonsSettingsSelectLessonsPresenter(LessonsSettingsSelectLessonsInterface view, Context context){
        this.view = view;
        this.lessonsInteractor = new LessonsInteractor(context);
        this.sharedPreferenceInteractor = new SharedPreferenceInteractor(context);
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
        return lessonsInteractor.getLessons();
    }

    public void resetProgress(){
        view.resetProgress();
    }

    public void resetScore(){
        view.resetScore();
    }

    public void resetIntensity(){
        view.resetIntensity();
    }

    public void resetProgressForLessons(List<Lesson> lessonsList){
        for(Lesson lesson : lessonsList){
            sharedPreferenceInteractor.setLessonProgress(lesson.getId(), "Uncompleted");
        }

    }

    public void resetScoreForLessons(List<Lesson> lessonsList){
        for(Lesson lesson : lessonsList){
            sharedPreferenceInteractor.setLessonBestScore(lesson.getId(), "0");
        }
    }

    public void resetIntensityForLessons(List<Lesson> lessonList){
        for(Lesson lesson : lessonList){
            sharedPreferenceInteractor.setLessonIntensity(lesson.getId(), "1");
        }
    }

    public void closeFragment(){
        view.closeFragment();
    }
}
