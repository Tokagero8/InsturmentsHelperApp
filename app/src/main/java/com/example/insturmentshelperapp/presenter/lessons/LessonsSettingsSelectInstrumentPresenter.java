package com.example.insturmentshelperapp.presenter.lessons;

import android.content.Context;

import com.example.insturmentshelperapp.interactor.InstrumentInteractor;
import com.example.insturmentshelperapp.interactor.SharedPreferenceInteractor;
import com.example.insturmentshelperapp.model.Instrument;
import com.example.insturmentshelperapp.presenter.Presenter;
import com.example.insturmentshelperapp.view.lessons.LessonsSettingsSelectInstrumentInterface;

import java.util.List;

public class LessonsSettingsSelectInstrumentPresenter implements Presenter {

    private LessonsSettingsSelectInstrumentInterface view;
    private InstrumentInteractor instrumentInteractor;
    private SharedPreferenceInteractor sharedPreferenceInteractor;

    public LessonsSettingsSelectInstrumentPresenter(LessonsSettingsSelectInstrumentInterface view ,Context context){
        this.view = view;
        this.instrumentInteractor = new InstrumentInteractor(context);
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

    public List<Instrument> getInstruments(){
        return instrumentInteractor.getInstrumentList();
    }

    public void setInstrumentAndCloseFragment(Object object){
        Instrument instrument = (Instrument) object;
        sharedPreferenceInteractor.setLessonInstrumentId(instrument.getId());
        view.closeFragment();
    }
}
