package com.example.insturmentshelperapp.presenter.lessons;

import android.content.Context;

import com.example.insturmentshelperapp.database.AppDatabaseRepository;
import com.example.insturmentshelperapp.interactor.SharedPreferenceInteractor;
import com.example.insturmentshelperapp.model.InstrumentRepeatability;
import com.example.insturmentshelperapp.presenter.Presenter;
import com.example.insturmentshelperapp.view.lessons.LessonsSettingsInterface;

public class LessonsSettingsPresenter implements Presenter {

    LessonsSettingsInterface view;
    SharedPreferenceInteractor sharedPreferenceInteractor;
    AppDatabaseRepository appDatabaseRepository;

    public LessonsSettingsPresenter(LessonsSettingsInterface view, Context context){
        this.view = view;
        this.sharedPreferenceInteractor = new SharedPreferenceInteractor(context);
        this.appDatabaseRepository = new AppDatabaseRepository(context);
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

    public void setSelectedInstrument(){
        int instrumentId = sharedPreferenceInteractor.getSelectedLessonInstrumentsId();
        String instrmentName = appDatabaseRepository.getInstrumentName(instrumentId);
        String instrumentImage = appDatabaseRepository.getInstrumentImage(instrumentId);
        view.setInstrumentName(instrmentName);
        view.setInstrumentImage(instrumentImage);
    }

    public void setSelectedPreferredLength(){
        int instrumentId = sharedPreferenceInteractor.getSelectedLessonInstrumentsId();
        int intensityId = sharedPreferenceInteractor.getSelectedLessonInstrumentIntensity(instrumentId);
        view.setSelectedPreferredLength(intensityId);
    }

    public void setSelectedPreferredTime(){
        int instrumentId = sharedPreferenceInteractor.getSelectedLessonInstrumentsId();
        int hour = sharedPreferenceInteractor.getSelectedLessonInstrumentPreferredTimeHour(instrumentId);
        int minute = sharedPreferenceInteractor.getSelectedLessonInstrumentPreferredTimeMinute(instrumentId);
        view.setSelectedPreferredTime(hour, minute);
    }

    public void setSelectedPreferredRepeatability(){
        int instrumentId = sharedPreferenceInteractor.getSelectedLessonInstrumentsId();
        int repeatability = sharedPreferenceInteractor.getSelectedLessonInstrumentRepeatability(instrumentId);
        int[] repeatabilityList = InstrumentRepeatability.getRepeatabilityList();
        view.setSelectedPreferredRepeatability(repeatabilityList, repeatability);
    }

    public void setSelectedPreferredUseOfIntensity(){
        int instrumentId = sharedPreferenceInteractor.getSelectedLessonInstrumentsId();
        int useOfIntensity = sharedPreferenceInteractor.getSelectedLessonInstrumentUseOfIntensity(instrumentId);
        view.setSelectedPreferredUseOfIntensity(useOfIntensity);
    }

    public void runInstrumentSelect(){
        view.runInstrumentSelect();
    }

    public void runPreferredLengthDialog(){
        view.runPreferredLengthDialog();
    }

    public void setLessonsLength(int position){
        int instrumentId = sharedPreferenceInteractor.getSelectedLessonInstrumentsId();
        String instrmentName = appDatabaseRepository.getInstrumentName(instrumentId);
        sharedPreferenceInteractor.setLessonInstrumentIntensity(instrmentName, position);
    }

    public void selectPreferredTime(){
        view.selectPreferredTime();
    }

    public void setPreferredTime(int hour, int minute){
        int instrumentId = sharedPreferenceInteractor.getSelectedLessonInstrumentsId();
        String instrmentName = appDatabaseRepository.getInstrumentName(instrumentId);
        sharedPreferenceInteractor.setLessonInstrumentPreferredTimeHour(instrmentName, hour);
        sharedPreferenceInteractor.setLessonInstrumentPreferredTimeMinute(instrmentName, minute);
    }

    public void runPreferredRepeatabilityDialog(){
        view.runPreferredRepeatabilityDialog();
    }

    public void setLessonsRepeatability(int position){
        int instrumentId = sharedPreferenceInteractor.getSelectedLessonInstrumentsId();
        String instrumentName = appDatabaseRepository.getInstrumentName(instrumentId);
        sharedPreferenceInteractor.setLessonInstrumentRepeatability(instrumentName, position);
    }

    public void setPreferredUseOfIntensity(boolean isTrue){
        int instrumentId = sharedPreferenceInteractor.getSelectedLessonInstrumentsId();
        String instrmentName = appDatabaseRepository.getInstrumentName(instrumentId);
        if(isTrue){
            sharedPreferenceInteractor.setLessonInstrumentUseOfIntensity(instrmentName, 1);
        }else{
            sharedPreferenceInteractor.setLessonInstrumentUseOfIntensity(instrmentName, 0);
        }
    }
}
