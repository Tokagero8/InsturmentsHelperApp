package com.example.insturmentshelperapp.presenter.chordbook;

import android.content.Context;

import com.example.insturmentshelperapp.interactor.ChordInteractor;
import com.example.insturmentshelperapp.model.Chord;
import com.example.insturmentshelperapp.model.ChordWithBitmap;
import com.example.insturmentshelperapp.presenter.Presenter;
import com.example.insturmentshelperapp.view.chordbook.ChordsBookChordsVariantsInterface;

import java.util.List;

public class ChordsBookChordsVariantsPresenter implements Presenter {

    private ChordsBookChordsVariantsInterface chordsBookView;
    private ChordInteractor chordInteractor;

    public ChordsBookChordsVariantsPresenter(ChordsBookChordsVariantsInterface view, Context context){
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

    public List<ChordWithBitmap> getChordsVariantsForChord(Chord chord, float targetHeight){
        return chordInteractor.getChordsVariantsForChord(chord, targetHeight);
    }

    public void runChordFromPosition(Chord chord){
        chordsBookView.runFragmentChord(chord);
    }
}
