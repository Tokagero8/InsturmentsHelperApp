package com.example.insturmentshelperapp.view.lessons;

public interface LessonsSettingsInterface {
    void setInstrumentName(String instrumentName);
    void setInstrumentImage(String instrumentImage);
    void setSelectedPreferredLength(int intensityId);
    void setSelectedPreferredTime(int hour, int minute);
    void setSelectedPreferredRepeatability(int[]repeatabilityList, int repeatability);
    void setSelectedPreferredUseOfIntensity(int useOfIntensity);
    void runInstrumentSelect();
    void runPreferredLengthDialog();
    void selectPreferredTime();
    void runPreferredRepeatabilityDialog();
}
