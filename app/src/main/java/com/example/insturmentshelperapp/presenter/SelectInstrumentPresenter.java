package com.example.insturmentshelperapp.presenter;

import android.content.Context;

import com.example.insturmentshelperapp.interactor.InstrumentInteractor;
import com.example.insturmentshelperapp.interactor.SharedPreferenceInteractor;
import com.example.insturmentshelperapp.model.Instrument;
import com.example.insturmentshelperapp.view.SelectInstrumentInterface;

import java.util.List;

public class SelectInstrumentPresenter implements Presenter {

    private SelectInstrumentInterface tunerView;
    private InstrumentInteractor instrumentInteractor;
    private SharedPreferenceInteractor sharedPreferenceInteractor;

    public SelectInstrumentPresenter(SelectInstrumentInterface view, Context context){
        this.tunerView = view;
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

    public void setInstrumentAndRunTuner(Object object){
        Instrument instrument = (Instrument) object;
        sharedPreferenceInteractor.setInstrument(instrument.getId());
        tunerView.runTuner();
    }
}
