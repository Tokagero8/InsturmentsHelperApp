package com.example.insturmentshelperapp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.insturmentshelperapp.interactor.SharedPreferenceInteractor;
import com.example.insturmentshelperapp.model.Chord;
import com.example.insturmentshelperapp.model.Instrument;
import com.example.insturmentshelperapp.model.Lesson;
import com.example.insturmentshelperapp.model.Tuning;

import java.util.ArrayList;
import java.util.List;

public class AppDatabaseRepository {
    Context context;
    AppDatabaseHelper appDatabaseHelper;
    SharedPreferenceInteractor sharedPreferenceInteractor;

    public AppDatabaseRepository(Context context) {
        this.context = context;
        this.appDatabaseHelper = AppDatabaseHelper.getInstance(context);
        this.sharedPreferenceInteractor = new SharedPreferenceInteractor(context, this);
    }

    //Instrument
    public int getFirstInstrumentId() {
        SQLiteDatabase sqLiteDatabase = appDatabaseHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT id FROM instrument", null);
        cursor.moveToFirst();
        int instrumentId = cursor.getInt(cursor.getColumnIndex("id"));
        cursor.close();
        return instrumentId;
    }

    public List<Instrument> getAllInstruments() {
        SQLiteDatabase sqLiteDatabase = appDatabaseHelper.getReadableDatabase();
        List<Instrument> instrumentList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT id, name, strings_number, image, description FROM instrument", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int strings_number = cursor.getInt(cursor.getColumnIndex("strings_number"));
                String image = cursor.getString(cursor.getColumnIndex("image"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                instrumentList.add(new Instrument(id, name, strings_number, image, description));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return instrumentList;
    }

    public String getInstrumentStringsNumber(int instrumentId) {
        SQLiteDatabase sqLiteDatabase = appDatabaseHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT strings_number FROM instrument WHERE id=?", new String[]{String.valueOf(instrumentId)});
        cursor.moveToFirst();
        int stringsNumber = cursor.getInt(cursor.getColumnIndex("strings_number"));
        cursor.close();
        return String.valueOf(stringsNumber);
    }

    public String getInstrumentName(int instrumentId) {
        SQLiteDatabase sqLiteDatabase = appDatabaseHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT name FROM instrument WHERE id=?", new String[]{String.valueOf(instrumentId)});
        cursor.moveToFirst();
        String name = cursor.getString(cursor.getColumnIndex("name"));
        cursor.close();
        return name;
    }

    public String getInstrumentImage(int instrumentId){
        SQLiteDatabase sqLiteDatabase = appDatabaseHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT image FROM instrument WHERE id=?", new String[]{String.valueOf(instrumentId)});
        cursor.moveToFirst();
        String image = cursor.getString(cursor.getColumnIndex("image"));
        cursor.close();
        return image;
    }

    //Tuning
    public int getFirstTuningForInstrument(int instrumentId) {
        SQLiteDatabase sqLiteDatabase = appDatabaseHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT id FROM tuning WHERE instrument_id=?", new String[]{String.valueOf(instrumentId)});
        cursor.moveToFirst();
        int tuningId = cursor.getInt(cursor.getColumnIndex("id"));
        cursor.close();
        return tuningId;
    }

    public String getTuningName(int tuningId) {
        SQLiteDatabase sqLiteDatabase = appDatabaseHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT name FROM tuning WHERE id=?", new String[]{String.valueOf(tuningId)});
        cursor.moveToFirst();
        String name = cursor.getString(cursor.getColumnIndex("name"));
        cursor.close();
        return name;
    }

    public String getTuningPitches(int tuningId) {
        SQLiteDatabase sqLiteDatabase = appDatabaseHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT pitch_array FROM tuning WHERE id=?", new String[]{String.valueOf(tuningId)});
        cursor.moveToFirst();
        String pitchArray = cursor.getString(cursor.getColumnIndex("pitch_array"));
        cursor.close();
        return pitchArray;
    }

    public List<Tuning> getTuningsForInstrument(int insturmentId) {
        SQLiteDatabase sqLiteDatabase = appDatabaseHelper.getReadableDatabase();
        List<Tuning> tunings = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT id, name, pitch_array FROM tuning WHERE instrument_id =?", new String[]{String.valueOf(insturmentId)});
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String pitchArray = cursor.getString(cursor.getColumnIndex("pitch_array"));
                tunings.add(new Tuning(id, name, pitchArray, insturmentId));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tunings;
    }

    //Chords
    public List<Chord> getChordsByToneAndInstrumentId(String tone, int instrumentId) {
        SQLiteDatabase sqLiteDatabase = appDatabaseHelper.getReadableDatabase();
        List<Chord> chords = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT id, type, tabs, is_main FROM chord WHERE tone=? AND instrument_id=?", new String[]{tone, String.valueOf(instrumentId)});
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String type = cursor.getString(cursor.getColumnIndex("type"));
                String tabs = cursor.getString(cursor.getColumnIndex("tabs"));
                int is_main = cursor.getInt(cursor.getColumnIndex("is_main"));
                chords.add(new Chord(id, tone, type, tabs, is_main, instrumentId));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return chords;
    }

    public List<Chord> getChordsVariantsForChord(Chord chord) {
        SQLiteDatabase sqLiteDatabase = appDatabaseHelper.getReadableDatabase();
        List<Chord> chords = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT id, tabs, is_main FROM chord WHERE tone=? AND type=? AND instrument_id=?",
                new String[]{chord.getTone(), chord.getType(), String.valueOf(chord.getInstrumentId())});
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String tabs = cursor.getString(cursor.getColumnIndex("tabs"));
                int is_main = cursor.getInt(cursor.getColumnIndex("is_main"));
                chords.add(new Chord(id, chord.getTone(), chord.getType(), tabs, is_main, chord.getInstrumentId()));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return chords;
    }

    //Lesson
    public List<Lesson> getLessonsForInstrument(int instrumentId) {
        SQLiteDatabase sqLiteDatabase = appDatabaseHelper.getReadableDatabase();
        List<Lesson> lessonList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM lesson WHERE instrument_id=?", new String[]{String.valueOf(instrumentId)});
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String chordsIds = cursor.getString(cursor.getColumnIndex("chords_ids"));
                String difficulty = cursor.getString(cursor.getColumnIndex("difficulty"));
                //
                String progress = sharedPreferenceInteractor.getLessonProgress(id);
                String bestScore = sharedPreferenceInteractor.getLessonBestScore(id);
                String intensity = sharedPreferenceInteractor.getLessonIntensity(id);
                //
                int lessonTuningId = cursor.getInt(cursor.getColumnIndex("lesson_tuning_id"));
                int lessonType = cursor.getInt(cursor.getColumnIndex("lesson_type"));
                lessonList.add(new Lesson(id, name, chordsIds, difficulty, progress, bestScore, intensity, instrumentId, lessonTuningId, lessonType));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lessonList;
    }

    public List<Lesson> getLessonsForInstrumentAndType(int instrumentId, int type){
        SQLiteDatabase sqLiteDatabase = appDatabaseHelper.getReadableDatabase();
        List<Lesson> lessonList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM lesson WHERE instrument_id=? AND lesson_type=?", new String[]{String.valueOf(instrumentId), String.valueOf(type)});
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String chordsIds = cursor.getString(cursor.getColumnIndex("chords_ids"));
                String difficulty = cursor.getString(cursor.getColumnIndex("difficulty"));
                //
                String progress = sharedPreferenceInteractor.getLessonProgress(id);
                String bestScore = sharedPreferenceInteractor.getLessonBestScore(id);
                String intensity = sharedPreferenceInteractor.getLessonIntensity(id);
                //
                int lessonTuningId = cursor.getInt(cursor.getColumnIndex("lesson_tuning_id"));
                int lessonType = cursor.getInt(cursor.getColumnIndex("lesson_type"));
                lessonList.add(new Lesson(id, name, chordsIds, difficulty, progress, bestScore, intensity, instrumentId, lessonTuningId, lessonType));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lessonList;
    }

    public List<Chord> getLessonChords(List<String> chords, int instrumentId){
        SQLiteDatabase sqLiteDatabase = appDatabaseHelper.getReadableDatabase();
        List<Chord> lessonChords = new ArrayList<>();
        for(String chord :chords){{
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM lessonchord WHERE id=?", new String[]{chord});
            if(cursor.moveToFirst()){
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String tone = cursor.getString(cursor.getColumnIndex("tone"));
                String type = cursor.getString(cursor.getColumnIndex("type"));
                String tabs =cursor.getString(cursor.getColumnIndex("tabs"));
                lessonChords.add(new Chord(id, tone, type, tabs, true, instrumentId));
            }
        }}
        return lessonChords;
    }

    public Chord getLessonChord(int lessonChordId){
        SQLiteDatabase sqLiteDatabase = appDatabaseHelper.getReadableDatabase();
        Chord lessonChords = new Chord();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM lessonchord WHERE id=?", new String[]{String.valueOf(lessonChordId)});
        if(cursor.moveToFirst()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String tone = cursor.getString(cursor.getColumnIndex("tone"));
            String type = cursor.getString(cursor.getColumnIndex("type"));
            String tabs =cursor.getString(cursor.getColumnIndex("tabs"));
            lessonChords= new Chord(id, tone, type, tabs, true, -1);
        }
        return lessonChords;
    }

    public String getFrequencesForLessonTuning(int tunignId){
        SQLiteDatabase sqLiteDatabase = appDatabaseHelper.getReadableDatabase();
        String frequences = "";
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT frequences_array FROM lessontuning WHERE id =?", new String[]{String.valueOf(tunignId)});
        if(cursor.moveToFirst()){
            frequences = cursor.getString(cursor.getColumnIndex("frequences_array"));
        }
        return frequences;
    }

}
