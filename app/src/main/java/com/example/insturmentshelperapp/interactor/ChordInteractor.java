package com.example.insturmentshelperapp.interactor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;

import com.example.insturmentshelperapp.database.AppDatabaseRepository;
import com.example.insturmentshelperapp.model.Chord;
import com.example.insturmentshelperapp.model.ChordWithBitmap;
import com.example.insturmentshelperapp.model.Lesson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class ChordInteractor {

    private AppDatabaseRepository appDatabaseRepository;
    private SharedPreferenceInteractor sharedPreferenceInteractor;
    private Context context;

    public ChordInteractor(Context context){
        this.context = context;
        this.appDatabaseRepository = new AppDatabaseRepository(context);
        this.sharedPreferenceInteractor = new SharedPreferenceInteractor(context);
    }

    public List<ChordWithBitmap> getChordsForTone(String tone, float targetHeight){
        int instrumentId = sharedPreferenceInteractor.getSelectedInstrumentId();

        List<Chord> chords = appDatabaseRepository.getChordsByToneAndInstrumentId(tone, instrumentId);

        if(chords.isEmpty()){
            return new ArrayList<>();
        }

        List<String> uniqueTypes = getUniqueTypes(chords);
        List<Chord> uniqueChordsByType = getUniqueChordsByType(chords, uniqueTypes);

        return getChordTypeBitmaps(uniqueChordsByType, targetHeight);
    }

    public List<ChordWithBitmap> getLessonChords(Lesson lesson, float targetHeight){
        List<String> chordsIdList = getLessonChordsId(lesson.getChords());
        List<Chord> chordsList = appDatabaseRepository.getLessonChords(chordsIdList, lesson.getInstrumentId());
        return getChordTypeBitmaps(chordsList, targetHeight);
    }

    public List<String> getLessonChordsId(String chords){
        List<String> lessonChordsIdList = new ArrayList<>();
        AtomicInteger count = new AtomicInteger();
        AtomicInteger firstLetter = new AtomicInteger(-1);
        AtomicInteger secondLetter = new AtomicInteger();
        while (secondLetter.get() >= 0) {
            secondLetter.set(chords.indexOf(",", firstLetter.get() + 1));
            if (secondLetter.get() >= 0) {
                lessonChordsIdList.add(chords.substring(firstLetter.get() + 1, secondLetter.get()));
            } else {
                lessonChordsIdList.add(chords.substring(firstLetter.get() + 1));
            }
            firstLetter.set(secondLetter.get());
            count.getAndIncrement();
        }
        return lessonChordsIdList;
    }

    public List<ChordWithBitmap> getChordsVariantsForChord(Chord chord, float targetHeight){
        List<Chord> chords = appDatabaseRepository.getChordsVariantsForChord(chord);
        return getChordTypeBitmaps(chords, targetHeight);
    }

    public Bitmap getBitmapForChord(Chord chord, float targetHeight){
        Bitmap mutableClearChord = getMutableClearChordForInstrument(chord.getInstrumentId());
        float scale = getScaleForHeightAndChord(targetHeight, mutableClearChord);

        return ChordDrawerInteractor.getChordFromTabs(chord.getTabs(), mutableClearChord, scale);
    }

    public ChordWithBitmap getLessonChordWithBitmap(Chord chord, float targetHeight){
        Bitmap mutableClearChord = getMutableClearChordForInstrument(chord.getInstrumentId());
        float scale = getScaleForHeightAndChord(targetHeight, mutableClearChord);

        return getChordTypeBitmap(chord, mutableClearChord, scale);
    }


    private List<String> getUniqueTypes(List<Chord> chords){
        List<String> uniqueTypesList = new ArrayList<>();
        for(Chord chord : chords){
            uniqueTypesList.add(chord.getType());
        }
        Set<String> uniqueTypesSet = new HashSet<>(uniqueTypesList);
        return new ArrayList<>(uniqueTypesSet);
    }

    private List<Chord> getUniqueChordsByType(List<Chord> chords, List<String> uniqueTypes){
        List<List<Chord>> listOfChordsList = new ArrayList<>();
        for(String type : uniqueTypes){
            List<Chord> tempChordsList = new ArrayList<>();
            for(Chord chord: chords){
                if(chord.getType().equals(type)){
                    tempChordsList.add(chord);
                }
            }
            listOfChordsList.add(tempChordsList);
        }
        List<Chord> uniqueChordsByType = new ArrayList<>();
        for(List<Chord> chordList : listOfChordsList){
            int isMainCount = 0;
            for(Chord chord: chordList){
                if(chord.getIsMain()){
                    uniqueChordsByType.add(chord);
                    isMainCount++;
                    break;
                }
            }
            if(isMainCount == 0){
                uniqueChordsByType.add(chordList.get(0));
            }
        }
        return uniqueChordsByType;
    }

    private List<ChordWithBitmap> getChordTypeBitmaps(List<Chord> chords, float targetHeight){
        Bitmap mutableClearChord = getMutableClearChordForInstrument(chords.get(0).getInstrumentId());
        float scale = getScaleForHeightAndChord(targetHeight, mutableClearChord);

        return getChordTypeBitmaps(chords, mutableClearChord, scale);
    }

    private Bitmap getMutableClearChordForInstrument(int instrumentId){
        final String clearChordName = "clear_chord_" + appDatabaseRepository.getInstrumentStringsNumber(instrumentId);
        final int clearChordId =  context.getResources().getIdentifier(clearChordName, "drawable", context.getPackageName());
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), clearChordId, options);
        options.inScaled = false;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(context.getResources(), clearChordId, options).copy(Bitmap.Config.ARGB_8888, true);
    }

    private float getScaleForHeightAndChord(float targetHeight, Bitmap chord){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.density*targetHeight / chord.getHeight();
    }

    private List<ChordWithBitmap> getChordTypeBitmaps(List<Chord> chords, Bitmap mutableClearChord, float scale){
        List<ChordWithBitmap> chordTypeBitmaps = new ArrayList<>();
        for(Chord chord : chords){
            chordTypeBitmaps.add(new ChordWithBitmap(chord, ChordDrawerInteractor.getChordFromTabs(chord.getTabs(), mutableClearChord, scale)));
        }
        return chordTypeBitmaps;
    }

    private ChordWithBitmap getChordTypeBitmap(Chord chord, Bitmap mutableClearChord, float scale){
        return new ChordWithBitmap(chord, ChordDrawerInteractor.getChordFromTabs(chord.getTabs(), mutableClearChord, scale));
    }

}
