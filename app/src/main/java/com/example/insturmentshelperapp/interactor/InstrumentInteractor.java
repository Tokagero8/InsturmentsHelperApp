package com.example.insturmentshelperapp.interactor;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;

import com.example.insturmentshelperapp.database.AppDatabaseRepository;
import com.example.insturmentshelperapp.model.Instrument;
import com.example.insturmentshelperapp.model.Tuning;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class InstrumentInteractor {

    Context context;
    private AppDatabaseRepository appDatabaseRepository;
    private SharedPreferenceInteractor sharedPreferenceInteractor;


    public InstrumentInteractor(Context context){
        this.context = context;
        appDatabaseRepository = new AppDatabaseRepository(context);
        sharedPreferenceInteractor = new SharedPreferenceInteractor(context);
    }

    public List<Instrument> getInstrumentList(){
        return appDatabaseRepository.getAllInstruments();
    }

    public List<SpannableString> getSpannablePitchesList(){
        List<SpannableString> spannablePitchesList = new ArrayList<>();
        List<String> instrumentPitchesList = getPitchesList();
        for (String pitch : instrumentPitchesList) {
            SpannableString spannableStringPitch = new SpannableString(pitch);
            if (pitch.charAt(1) == '#') {
                spannableStringPitch.setSpan(new RelativeSizeSpan(0.5f), 1, 3, 0);
            } else {
                spannableStringPitch.setSpan(new RelativeSizeSpan(0.5f), 1, 2, 0);
            }
            spannablePitchesList.add(spannableStringPitch);
        }
        return spannablePitchesList;
    }

    public List<String> getPitchesList(){
        int tuningId = sharedPreferenceInteractor.getSelectedTuningId();
        String instrumentPitches = appDatabaseRepository.getTuningPitches(tuningId);
        List<String> instrumentPitchesList = new ArrayList<>();

        AtomicInteger count = new AtomicInteger();
        AtomicInteger firstLetter = new AtomicInteger(-1);
        AtomicInteger secondLetter = new AtomicInteger();

        while (secondLetter.get() >= 0) {
            secondLetter.set(instrumentPitches.indexOf(",", firstLetter.get() + 1));
            if (secondLetter.get() >= 0) {
                instrumentPitchesList.add(instrumentPitches.substring(firstLetter.get() + 1, secondLetter.get()));
            } else {
                instrumentPitchesList.add(instrumentPitches.substring(firstLetter.get() + 1));
            }
            firstLetter.set(secondLetter.get());
            count.getAndIncrement();
        }
        return instrumentPitchesList;
    }

    public List<Tuning> getTuningsForSelectedInstrument(){
        int instrumentId = sharedPreferenceInteractor.getSelectedInstrumentId();
        return appDatabaseRepository.getTuningsForInstrument(instrumentId);
    }

}
