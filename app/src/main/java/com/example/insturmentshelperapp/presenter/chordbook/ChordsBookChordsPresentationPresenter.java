package com.example.insturmentshelperapp.presenter.chordbook;

import android.content.Context;

import com.example.insturmentshelperapp.interactor.ChordInteractor;
import com.example.insturmentshelperapp.model.Chord;
import com.example.insturmentshelperapp.model.ChordWithBitmap;
import com.example.insturmentshelperapp.presenter.Presenter;
import com.example.insturmentshelperapp.view.chordbook.ChordsBookChordsPresentationInterface;

import java.util.List;

public class ChordsBookChordsPresentationPresenter implements Presenter {

    private ChordsBookChordsPresentationInterface chordsBookView;
    private ChordInteractor chordInteractor;

    public ChordsBookChordsPresentationPresenter(ChordsBookChordsPresentationInterface view, Context context){
        this.chordsBookView = view;
        this.chordInteractor = new ChordInteractor(context);
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

    public List<ChordWithBitmap> getChordsForTone(String tone, float targetHeight){
        return chordInteractor.getChordsForTone(tone, targetHeight);
    }

    public void runTypeFromPosition(Chord chord){
        chordsBookView.runFragmentWithChordVariants(chord);
    }
}
