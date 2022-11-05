package com.example.insturmentshelperapp.presenter.chordbook;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.insturmentshelperapp.interactor.ChordInteractor;
import com.example.insturmentshelperapp.model.Chord;
import com.example.insturmentshelperapp.presenter.Presenter;
import com.example.insturmentshelperapp.view.chordbook.ChordsBookChordDetailInterface;

public class ChordsBookChordDetailPresenter implements Presenter {

    private ChordsBookChordDetailInterface chordsBookView;
    private ChordInteractor chordInteractor;

    public ChordsBookChordDetailPresenter(ChordsBookChordDetailInterface view, Context context){
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

    public Bitmap getBitmapForChord(Chord chord, float targetHeight){
        return chordInteractor.getBitmapForChord(chord, targetHeight);
    }


}
