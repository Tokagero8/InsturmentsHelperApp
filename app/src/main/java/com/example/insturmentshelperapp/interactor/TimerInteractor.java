package com.example.insturmentshelperapp.interactor;

import android.content.Context;
import android.os.CountDownTimer;

import com.example.insturmentshelperapp.presenter.lessons.LessonsPresenterTimerInterface;

public class TimerInteractor {

    Context context;
    LessonsPresenterTimerInterface presenter;
    CountDownTimer timer;
    int time;
    int timerCounter = 0;

    public TimerInteractor(LessonsPresenterTimerInterface presenter, Context context) {
        this.presenter = presenter;
        this.context = context;
    }

    public void setTimer(final int time) {
        this.time = time;
        timer = new CountDownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long l) {
                timerCounter++;
                System.out.println(time - timerCounter);
                presenter.setNewTime(time - timerCounter);
            }
            @Override
            public void onFinish() {
                presenter.finishLesson();
            }
        };
    }

    public void startTimer(){
        timer.start();
    }

    public void stopTimer(){
        try{
            timer.cancel();
        }catch (NullPointerException e){
            System.out.println(e);
        }

    }
}
