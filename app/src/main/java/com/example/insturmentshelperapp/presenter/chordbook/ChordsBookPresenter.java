package com.example.insturmentshelperapp.presenter.chordbook;

import com.example.insturmentshelperapp.model.TonesNames;
import com.example.insturmentshelperapp.presenter.Presenter;
import com.example.insturmentshelperapp.view.chordbook.ChordsBookInterface;

import java.util.ArrayList;
import java.util.Arrays;

public class ChordsBookPresenter implements Presenter {

    private ChordsBookInterface chordsBookView;
    private TonesNames tonesNames;

    public ChordsBookPresenter(ChordsBookInterface view){
        this.chordsBookView = view;
        this.tonesNames = new TonesNames();
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

    public ArrayList<String> getToneNames(){
        return new ArrayList<>(Arrays.asList(tonesNames.getTonesNames())) ;
    }

    public void runToneFromPosition(int position){
        chordsBookView.runFragmentWithTone(tonesNames.getToneName(position));
    }
}
