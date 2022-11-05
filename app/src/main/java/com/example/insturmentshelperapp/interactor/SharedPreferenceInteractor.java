package com.example.insturmentshelperapp.interactor;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.insturmentshelperapp.database.AppDatabaseRepository;



public class SharedPreferenceInteractor {
    private Context context;
    AppDatabaseRepository appDatabaseRepository;

    public SharedPreferenceInteractor(Context context){
        this.context = context;
        this.appDatabaseRepository = new AppDatabaseRepository(context);
    }

    public SharedPreferenceInteractor(Context context, AppDatabaseRepository appDatabaseRepository){
        this.context = context;
        this.appDatabaseRepository = appDatabaseRepository;
    }

    public int getSelectedInstrumentId(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("instrumentPreferences", Context.MODE_PRIVATE);
        int instrumentId = sharedPreferences.getInt("instrumentid", -1);
        if(instrumentId != -1){
            return instrumentId;
        } else {
            setFirstInstrument();
            return sharedPreferences.getInt("instrumentid", -1);
        }
    }

    public void setInstrument(int instrumentId){
        SharedPreferences sharedPreferences = context.getSharedPreferences("instrumentPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("instrumentid", instrumentId);
        editor.apply();
        setFirstTuning();
    }

    public int getSelectedTuningId(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("instrumentPreferences", Context.MODE_PRIVATE);
        int tuningId = sharedPreferences.getInt("tuningid", -1);
        if(tuningId != -1){
            return  tuningId;
        } else {
            setFirstTuning();
            return sharedPreferences.getInt("tuningid", -1);
        }
    }

    public void setTuning(int tuningId){
        SharedPreferences sharedPreferences = context.getSharedPreferences("instrumentPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("tuningid", tuningId);
        editor.apply();
    }





    public int getSelectedLessonInstrumentsId(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("instrumentPreferences", Context.MODE_PRIVATE);
        int instrumentId = sharedPreferences.getInt("lessoninstrumentid", -1);
        if(instrumentId != -1){
            return instrumentId;
        } else {
            setFirstLessonInstrument();
            return sharedPreferences.getInt("lessoninstrumentid", -1);
        }
    }

    public void setLessonInstrumentId(int id){
        SharedPreferences sharedPreferences = context.getSharedPreferences("instrumentPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("lessoninstrumentid", id);
        editor.apply();
    }

    public void setFirstLessonInstrument(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("instrumentPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int instrumentId = appDatabaseRepository.getFirstInstrumentId();
        editor.putInt("lessoninstrumentid", instrumentId);
        editor.apply();
    }

    public int getSelectedLessonInstrumentIntensity(int id){
        SharedPreferences sharedPreferences = context.getSharedPreferences("instrumentPreferences", Context.MODE_PRIVATE);
        String instrumentName = appDatabaseRepository.getInstrumentName(id);
        int instrumentIntensity = sharedPreferences.getInt("lessoninstrument" + instrumentName + "intensity", -1);
        if(instrumentIntensity != -1){
            return instrumentIntensity;
        }else {
            setLessonInstrumentIntensity(instrumentName, 1);
            return sharedPreferences.getInt("lessoninstrument" + instrumentName + "intensity", -1);
        }
    }

    public void setLessonInstrumentIntensity(String instrumentName, int id){
        SharedPreferences sharedPreferences = context.getSharedPreferences("instrumentPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("lessoninstrument" + instrumentName + "intensity", id);
        editor.apply();
    }

    public int getSelectedLessonInstrumentRepeatability(int id){
        SharedPreferences sharedPreferences = context.getSharedPreferences("instrumentPreferences", Context.MODE_PRIVATE);
        String instrumentName = appDatabaseRepository.getInstrumentName(id);
        int instrumentRepeatability = sharedPreferences.getInt("lessoninstrument" + instrumentName + "repeatability", -1);
        if(instrumentRepeatability != -1){
            return  instrumentRepeatability;
        }else{
            setLessonInstrumentRepeatability(instrumentName, 1);
            return sharedPreferences.getInt("lessoninstrument" + instrumentName + "repeatability", -1);
        }
    }

    public void setLessonInstrumentRepeatability(String instrumentName, int value){
        SharedPreferences sharedPreferences = context.getSharedPreferences("instrumentPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("lessoninstrument" + instrumentName + "repeatability", value);
        editor.apply();
    }

    public int getSelectedLessonInstrumentPreferredTimeHour(int id){
        SharedPreferences sharedPreferences = context.getSharedPreferences("instrumentPreferences", Context.MODE_PRIVATE);
        String instrumentName = appDatabaseRepository.getInstrumentName(id);
        int instrumentPreferredTime = sharedPreferences.getInt("lessoninstrument" + instrumentName + "preferredtimehour", -1);
        if(instrumentPreferredTime != -1){
            return instrumentPreferredTime;
        }
        else{
            setLessonInstrumentPreferredTimeHour(instrumentName, 17);
            return sharedPreferences.getInt("lessoninstrument" + instrumentName + "preferredtimehour", -1);
        }
    }

    public void setLessonInstrumentPreferredTimeHour(String instrumentName, int hourOfDay){
        SharedPreferences sharedPreferences = context.getSharedPreferences("instrumentPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("lessoninstrument" + instrumentName + "preferredtimehour", hourOfDay);
        editor.apply();
    }

    public int getSelectedLessonInstrumentPreferredTimeMinute(int id){
        SharedPreferences sharedPreferences = context.getSharedPreferences("instrumentPreferences", Context.MODE_PRIVATE);
        String instrumentName = appDatabaseRepository.getInstrumentName(id);
        int instrumentPreferredTime = sharedPreferences.getInt("lessoninstrument" + instrumentName + "preferredtimeminute", -1);
        if(instrumentPreferredTime != -1){
            return instrumentPreferredTime;
        }
        else{
            setLessonInstrumentPreferredTimeMinute(instrumentName, 0);
            return sharedPreferences.getInt("lessoninstrument" + instrumentName + "preferredtimeminute", -1);
        }
    }

    public void setLessonInstrumentPreferredTimeMinute(String instrumentName, int minute){
        SharedPreferences sharedPreferences = context.getSharedPreferences("instrumentPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("lessoninstrument" + instrumentName + "preferredtimeminute", minute);
        editor.apply();
    }

    public int getSelectedLessonInstrumentUseOfIntensity(int id){
        SharedPreferences sharedPreferences = context.getSharedPreferences("instrumentPreferences", Context.MODE_PRIVATE);
        String instrumentName = appDatabaseRepository.getInstrumentName(id);
        int instrumentUseOfIntensity = sharedPreferences.getInt("lessoninstrument" + instrumentName + "useofintensity", -1);
        if(instrumentUseOfIntensity != -1){
            return instrumentUseOfIntensity;
        }else{
            setLessonInstrumentUseOfIntensity(instrumentName, 1);
            return sharedPreferences.getInt("lessoninstrument" + instrumentName + "useofintensity", -1);
        }
    }

    public void setLessonInstrumentUseOfIntensity(String instrumentName, int value){
        SharedPreferences sharedPreferences = context.getSharedPreferences("instrumentPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("lessoninstrument" + instrumentName + "useofintensity", value);
        editor.apply();
    }

    public String getSelectedInstrumentName(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("instrumentPreferences", Context.MODE_PRIVATE);
        int instrumentId = sharedPreferences.getInt("instrumentid", -1);
        if (instrumentId == -1) {
            setFirstInstrument();
            instrumentId = sharedPreferences.getInt("instrumentid", -1);
        }
        return appDatabaseRepository.getInstrumentName(instrumentId);
    }

    public String getSelectedTuningName(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("instrumentPreferences", Context.MODE_PRIVATE);
        int tuningId = sharedPreferences.getInt("tuningid", -1);
        if (tuningId == -1) {
            setFirstTuning();
            tuningId = sharedPreferences.getInt("tuningid", -1);
        }
        return appDatabaseRepository.getTuningName(tuningId);
    }

    public void setFirstInstrument(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("instrumentPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int instrumentId = appDatabaseRepository.getFirstInstrumentId();
        editor.putInt("instrumentid", instrumentId);
        editor.apply();
    }

    public void setFirstTuning(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("instrumentPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int instrumentId = sharedPreferences.getInt("instrumentid", -1);
        int tuningId = appDatabaseRepository.getFirstTuningForInstrument(instrumentId);
        editor.putInt("tuningid", tuningId);
        editor.apply();
    }

    public String getLessonProgress(int lessonId){
        SharedPreferences sharedPreferences = context.getSharedPreferences("instrumentPreferences", Context.MODE_PRIVATE);
        String progress = sharedPreferences.getString("lessonprogress" + lessonId, "-1");
        if(progress.equals("-1")){
            setLessonProgress(lessonId, "Uncompleted");
            return sharedPreferences.getString("lessonprogress" + lessonId, "-1");
        }else{
            return progress;
        }
    }

    public void setLessonProgress(int lessonId, String progress){
        SharedPreferences sharedPreferences = context.getSharedPreferences("instrumentPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lessonprogress" + lessonId, progress);
        editor.apply();
    }

    public String getLessonBestScore(int lessonId){
        SharedPreferences sharedPreferences = context.getSharedPreferences("instrumentPreferences", Context.MODE_PRIVATE);
        String bestScore = sharedPreferences.getString("lessonbestscore" + lessonId, "-1");
        if(bestScore.equals("-1")){
            setLessonBestScore(lessonId, "0");
            return sharedPreferences.getString("lessonbestscore" + lessonId, "-1");
        }else{
            return bestScore;
        }
    }

    public void setLessonBestScore(int lessonId, String bestScore){
        SharedPreferences sharedPreferences = context.getSharedPreferences("instrumentPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lessonbestscore" + lessonId, bestScore);
        editor.apply();
    }

    public String getLessonIntensity(int lessonId){
        SharedPreferences sharedPreferences = context.getSharedPreferences("instrumentPreferences", Context.MODE_PRIVATE);
        String bestScore = sharedPreferences.getString("lessonintensity" + lessonId, "-1");
        if(bestScore.equals("-1")){
            setLessonIntensity(lessonId, "1");
            return sharedPreferences.getString("lessonintensity" + lessonId, "-1");
        }else{
            return bestScore;
        }
    }

    public void setLessonIntensity(int lessonId, String intensity){
        SharedPreferences sharedPreferences = context.getSharedPreferences("instrumentPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lessonintensity" + lessonId, intensity);
        editor.apply();
    }

}
