package com.example.insturmentshelperapp.presenter.tuner;

public interface TunerPresenterInterface {
    double getSelectedButtonFrequence();
    void tooHighTone(double frequence, double cent);
    void tooLowTone(double frequence, double cent);
    void CorrectTone(double frequence, double cent, int correctCounter);
}
