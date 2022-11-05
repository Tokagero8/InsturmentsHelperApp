package com.example.insturmentshelperapp.presenter.lessons;

import android.content.Context;
import android.media.AudioFormat;
import android.media.MediaRecorder;

import com.example.insturmentshelperapp.database.AppDatabaseRepository;
import com.example.insturmentshelperapp.interactor.LessonsInteractor;
import com.example.insturmentshelperapp.interactor.SharedPreferenceInteractor;
import com.example.insturmentshelperapp.interactor.TimerInteractor;
import com.example.insturmentshelperapp.model.Chord;
import com.example.insturmentshelperapp.model.ChordWithBitmap;
import com.example.insturmentshelperapp.model.LessonsIntensity;
import com.example.insturmentshelperapp.model.Lesson;
import com.example.insturmentshelperapp.model.Recorder;
import com.example.insturmentshelperapp.presenter.Presenter;
import com.example.insturmentshelperapp.view.lessons.LessonsPlayInterface;

import java.util.List;

public class LessonsPlayPresenter implements Presenter, LessonsPlayPresenterInterface, LessonsPresenterTimerInterface {

    LessonsPlayInterface view;
    SharedPreferenceInteractor sharedPreferenceInteractor;
    TimerInteractor timer;
    LessonsInteractor lessonsInteractor;
    AppDatabaseRepository appDatabaseRepository;
    Recorder recorder;

    public LessonsPlayPresenter(LessonsPlayInterface view, Context context){
        this.view = view;
        this.sharedPreferenceInteractor = new SharedPreferenceInteractor(context);
        this.timer = new TimerInteractor(this, context);
        this.lessonsInteractor = new LessonsInteractor(context, this);
        this.appDatabaseRepository = new AppDatabaseRepository(context);
        this.recorder = new Recorder(MediaRecorder.AudioSource.MIC, 16000, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, 4096, 4);
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
    public int getLessonTuningId() {
        return view.getLessonTuningId();
    }

    @Override
    public Chord getSelectedChord() {
        return view.getSelectedChord();
    }

    @Override
    public void setValidationResult(List<Integer> resultList) {
        view.setValidationResult(resultList);
    }

    @Override
    public boolean getIsChordChanging() {
        return view.getIsChordChanging();
    }

    @Override
    public void finishLesson() {
        lessonsInteractor.stopListening();
        view.runSummaryFragment();
    }


    public void setTimer(Lesson lesson){
        int intensity = sharedPreferenceInteractor.getSelectedLessonInstrumentIntensity(lesson.getInstrumentId());
        int time = LessonsIntensity.getPlayLessonTime(intensity);
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

    public void startTimer(){
        timer.startTimer();
    }

    public void setChord(Lesson lesson, float targetHeight){
        ChordWithBitmap chordWithBitmap = lessonsInteractor.getRandomChordWithBitmap(lesson, targetHeight);
        view.setChord(chordWithBitmap);
    }

    public void setNextChord(Lesson lesson, float targetHeight, int currentChordId){
        ChordWithBitmap chordWithBitmap = lessonsInteractor.getRandomNextChordWithBitmap(lesson, targetHeight, currentChordId);
        view.setChord(chordWithBitmap);
    }

    public void setValidationCircles(Lesson lesson){
        int stringsNumber = Integer.parseInt(appDatabaseRepository.getInstrumentStringsNumber(lesson.getInstrumentId()));
        view.setValidationCircles(stringsNumber);
    }

    public void stopTimer(){
        timer.stopTimer();
    }

    public void startListening(){
        lessonsInteractor.startListening(recorder);
    }

    public void stopListening(){
        lessonsInteractor.stopListening();
    }

    public void drawResults(List<List<Integer>> resultsList){
        List<Integer> colorList =  lessonsInteractor.getColorsFromResults(resultsList);
        view.setResultColors(colorList);
    }


}
