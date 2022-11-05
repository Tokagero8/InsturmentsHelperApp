package com.example.insturmentshelperapp.presenter.tuner;

import android.content.Context;

import com.example.insturmentshelperapp.interactor.InstrumentInteractor;
import com.example.insturmentshelperapp.interactor.SharedPreferenceInteractor;
import com.example.insturmentshelperapp.model.Tuning;
import com.example.insturmentshelperapp.presenter.Presenter;
import com.example.insturmentshelperapp.view.tuner.TunerSelectTuningInterface;

import java.util.List;

public class TunerSelectTuningPresenter implements Presenter {

    private TunerSelectTuningInterface tunerView;
    private InstrumentInteractor instrumentInteractor;
    private SharedPreferenceInteractor sharedPreferenceInteractor;

    public TunerSelectTuningPresenter(TunerSelectTuningInterface view, Context context){
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

    public List<Tuning> getTunings(){
        return instrumentInteractor.getTuningsForSelectedInstrument();
    }

    public void setTuningAndRunTuner(Object object){
        Tuning tuning = (Tuning) object;
        sharedPreferenceInteractor.setTuning(tuning.getId());
        tunerView.runTuner();
    }


}
