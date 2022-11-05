package com.example.insturmentshelperapp.view.tuner;

import android.text.SpannableString;

public interface TunerInterface {
    void setSelectedInstrumentName(String instrumentName);
    void setSelectedTuningName(String tuningName);
    void clearChecksRight();
    void clearChecksLeft();
    void removeStringsSelectViews();
    void addLeftButtonWithName(SpannableString name);
    void addRightButtonWithName(SpannableString name);
    String getSelectedButtonPitchName();
    void setTuningInfo(String frequence, int color, double cent);
    void setButtonColor(int color);
}
