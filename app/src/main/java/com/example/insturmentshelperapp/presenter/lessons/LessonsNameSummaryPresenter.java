package com.example.insturmentshelperapp.presenter.lessons;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.insturmentshelperapp.interactor.AlertReceiver;
import com.example.insturmentshelperapp.interactor.LessonsInteractor;
import com.example.insturmentshelperapp.interactor.SharedPreferenceInteractor;
import com.example.insturmentshelperapp.model.InstrumentRepeatability;
import com.example.insturmentshelperapp.model.Lesson;
import com.example.insturmentshelperapp.model.LessonsIntensity;
import com.example.insturmentshelperapp.presenter.Presenter;
import com.example.insturmentshelperapp.view.lessons.LessonsNameSummaryInterface;

import java.util.Calendar;
import java.util.List;

public class LessonsNameSummaryPresenter implements Presenter {

    LessonsNameSummaryInterface view;
    Context context;
    SharedPreferenceInteractor sharedPreferenceInteractor;
    LessonsInteractor lessonsInteractor;

    public LessonsNameSummaryPresenter(LessonsNameSummaryInterface view, Context context){
        this.view = view;
        this.context = context;
        this.sharedPreferenceInteractor = new SharedPreferenceInteractor(context);
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

    public void setScores(Lesson lesson, String score) {
        view.setScore(score);
        int intensity = sharedPreferenceInteractor.getSelectedLessonInstrumentIntensity(lesson.getInstrumentId());
        int neededScore = LessonsIntensity.getNameLessonPoints(intensity);
        double lessonIntensityMultipler;
        if(sharedPreferenceInteractor.getSelectedLessonInstrumentUseOfIntensity(lesson.getInstrumentId()) > 0 ){
            lessonIntensityMultipler = Double.parseDouble(lesson.getIntensity());
        }else {
            lessonIntensityMultipler = 1;
        }
        view.setNeededScore(neededScore, lessonIntensityMultipler);
    }

    public void setResult(Lesson lesson, String score) {
        int intensity = sharedPreferenceInteractor.getSelectedLessonInstrumentIntensity(lesson.getInstrumentId());
        int neededScore = LessonsIntensity.getNameLessonPoints(intensity);
        double lessonIntensityMultipler;
        if(sharedPreferenceInteractor.getSelectedLessonInstrumentUseOfIntensity(lesson.getInstrumentId()) > 0 ){
            lessonIntensityMultipler = Double.parseDouble(lesson.getIntensity());
        }else {
            lessonIntensityMultipler = 1;
        }
        view.setResult(Integer.parseInt(score), neededScore, lessonIntensityMultipler);
    }

    public void setNextLesson(Lesson lesson, boolean isPassed, String score) {
        Lesson nextLesson = null;
        if (isPassed) {
            List<Lesson> lessonList = lessonsInteractor.getLessonsWithType(3);

            for(Lesson loopLesson : lessonList){
                if(loopLesson.getId() != lesson.getId() && !loopLesson.getProgress().equals("Completed")){
                    nextLesson = loopLesson;
                    break;
                }
            }

            lessonsInteractor.trySetBestScore(lesson, score);
            lessonsInteractor.resetIntensity(lesson);
            lessonsInteractor.setLessonComplete(lesson);

            int instrumentRepeatability = sharedPreferenceInteractor.getSelectedLessonInstrumentRepeatability(lesson.getInstrumentId());
            int repeatability = InstrumentRepeatability.getRepeatability(instrumentRepeatability);
            view.setNextLesson(nextLesson, repeatability);
        } else {
            lessonsInteractor.trySetBestScore(lesson, score);
            lessonsInteractor.increaseIntensity(lesson);

            int instrumentRepeatability = sharedPreferenceInteractor.getSelectedLessonInstrumentRepeatability(lesson.getInstrumentId());
            int repeatability = InstrumentRepeatability.getRepeatability(instrumentRepeatability);
            view.setNextLesson(lesson, repeatability);
        }
    }

    public Calendar getPreferedDate(Lesson lesson, int repeatability){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, repeatability);
        int hour = sharedPreferenceInteractor.getSelectedLessonInstrumentPreferredTimeHour(lesson.getInstrumentId());
        int minute = sharedPreferenceInteractor.getSelectedLessonInstrumentPreferredTimeMinute(lesson.getInstrumentId());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }

    public void setNotification(Calendar calendar, String nextLessonName){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlertReceiver.class);
        intent.putExtra("Title", "Next Lesson");
        intent.putExtra("Text", "Your next lesson has just started: " + nextLessonName);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 3, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public void notSetNotification(){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 3, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }

    public void disableButtons(){
        view.disableButtons();
    }
}
