package com.example.insturmentshelperapp.model;

import android.graphics.Bitmap;

public class ChordWithBitmap extends Chord {
    Bitmap bitmap;

    public ChordWithBitmap(Chord chord, Bitmap bitmap){
        this.id = chord.id;
        this.tone = chord.tone;
        this.type = chord.type;
        this.tabs = chord.tabs;
        this.isMain = chord.isMain;
        this.instrumentId = chord.instrumentId;
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap(){
        return bitmap;
    }
}
