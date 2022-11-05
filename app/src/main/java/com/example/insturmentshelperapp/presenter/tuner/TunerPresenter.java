package com.example.insturmentshelperapp.presenter.tuner;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;

import com.example.insturmentshelperapp.interactor.InstrumentInteractor;
import com.example.insturmentshelperapp.interactor.SharedPreferenceInteractor;
import com.example.insturmentshelperapp.interactor.TunerInteractor;
import com.example.insturmentshelperapp.model.Pitches;
import com.example.insturmentshelperapp.model.Recorder;
import com.example.insturmentshelperapp.presenter.Presenter;
import com.example.insturmentshelperapp.view.tuner.TunerInterface;

import java.util.List;

public class TunerPresenter implements Presenter, TunerPresenterInterface{

    private TunerInterface tunerView;
    private SharedPreferenceInteractor sharedPreference;
    private InstrumentInteractor instrumentInteractor;
    private TunerInteractor tunerInteractor;
    private Recorder recorder;

    public TunerPresenter(TunerInterface view, Context context){
        this.tunerView = view;
        this.sharedPreference = new SharedPreferenceInteractor(context);
        this.instrumentInteractor = new InstrumentInteractor(context);
        this.tunerInteractor = new TunerInteractor(context, this);
        this.recorder = new Recorder();
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
    public double getSelectedButtonFrequence() {
        String pitchName = tunerView.getSelectedButtonPitchName();
        if(pitchName.equals("-1")){
            return -1;
        } else {
            Pitches pitches = Pitches.getInstance();
            return pitches.findFrequenceFor(pitchName);
        }
    }

    @Override
    public void tooHighTone(double frequence, double cent) {
        tunerView.setTuningInfo(frequence + "Hz↓", Color.RED, cent);
    }

    @Override
    public void tooLowTone(double frequence, double cent) {
        tunerView.setTuningInfo(frequence + "Hz↑", Color.RED, cent);
    }

    @Override
    public void CorrectTone(double frequence, double cent, int correctCoutner) {
        tunerView.setTuningInfo(frequence + "Hz", Color.GREEN, cent);
        if(correctCoutner >= 3){
            tunerView.setButtonColor(Color.GREEN);
        }

    }

    public void setTextViewInstrumentName(){
        String instrumentName = sharedPreference.getSelectedInstrumentName();
        tunerView.setSelectedInstrumentName(instrumentName);
    }

    public void setTextViewTuningName(){
        String tunignName = sharedPreference.getSelectedTuningName();
        tunerView.setSelectedTuningName(tunignName);
    }

    public void clearChecksRight(){
        tunerView.clearChecksRight();
    }

    public void clearChecksLeft(){
        tunerView.clearChecksLeft();
    }

    public void setRadioButtons(){
        tunerView.removeStringsSelectViews();
        List<SpannableString> spannablePitchesList = instrumentInteractor.getSpannablePitchesList();
        for(int i = (spannablePitchesList.size()-1)/2; i>=0; i--){
            tunerView.addLeftButtonWithName(spannablePitchesList.get(i));
        }
        for(int i = (spannablePitchesList.size()-1)/2 + 1; i<spannablePitchesList.size(); i++){
            tunerView.addRightButtonWithName(spannablePitchesList.get(i));
        }
    }

    public void startListening(){
        tunerInteractor.startListening(recorder);
    }

    public void stopListening(){
        tunerInteractor.stopListening();
    }
}
