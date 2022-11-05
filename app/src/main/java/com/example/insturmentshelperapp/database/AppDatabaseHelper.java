package com.example.insturmentshelperapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AppDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "InstrumentHelpertAppDatabase";
    private static final int DB_VERSION = 1;
    private static AppDatabaseHelper instance = null;

    AppDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static AppDatabaseHelper getInstance(Context context){
        if(instance == null) {
            instance = new AppDatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        updateMyDatabase(sqLiteDatabase, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        updateMyDatabase(sqLiteDatabase, oldVersion, newVersion);
    }

    private void updateMyDatabase(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            String sqlDB = "CREATE TABLE instrument (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR UNIQUE, strings_number INTEGER, image VARCHAR, description VARCHAR)";
            sqLiteDatabase.execSQL(sqlDB);
            insertInstrument(sqLiteDatabase, "Guitar", 6, "guitar800", "Classic string instrument.");
            insertInstrument(sqLiteDatabase, "Ukulele", 4, "ukulele800", "Fun string instrument.");

            sqlDB = "CREATE TABLE tuning (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, pitch_array VARCHAR, instrument_id INTEGER, FOREIGN KEY(instrument_id) REFERENCES instrument(id))";
            sqLiteDatabase.execSQL(sqlDB);
            insertTuning(sqLiteDatabase, "Soprano", "G4,C4,E4,A4", "Ukulele");
            insertTuning(sqLiteDatabase, "Baritone", "D3,G3,B3,E4", "Ukulele");
            insertTuning(sqLiteDatabase, "Standard", "E2,A2,D3,G3,B3,E4", "Guitar");

            sqlDB = "CREATE TABLE lesson (id INTEGER PRIMARY KEY, name VARCHAR, chords_ids VARCHAR, difficulty VARCHAR,  " +
                    "instrument_id INTEGER, lesson_tuning_id INTEGER, lesson_type INTEGER, FOREIGN KEY(instrument_id) REFERENCES instrument(id), FOREIGN KEY(lesson_tuning_id) REFERENCES lessontuning(id))";
            sqLiteDatabase.execSQL(sqlDB);
            insertLesson(sqLiteDatabase, 1001, "Play:\nEm, Am, D, C", "8,17,4,1", "Easy",  "Guitar", "1", 1);
            insertLesson(sqLiteDatabase, 1002, "Play:\nE, A, Dm, G", "7,16,5,13", "Easy",  "Guitar", "1", 1);
            insertLesson(sqLiteDatabase, 1003, "Play:\nE7, D7, A7, G7", "9,6,18,15", "Easy",  "Guitar", "1", 1);
            insertLesson(sqLiteDatabase, 1004, "Play:\nF7, C7, H7, B7", "12,3,24,21", "Medium",  "Guitar", "1", 1);
            insertLesson(sqLiteDatabase, 1005, "Play:\nCm, H, Bm, F", "2,22,20,10", "Medium",  "Guitar", "1", 1);
            insertLesson(sqLiteDatabase, 1006, "Play:\nHm, Gm, Fm, B", "14,23,19,14", "Medium",  "Guitar", "1", 1);

            insertLesson(sqLiteDatabase, 2001, "Chords:\nEm, Am, D, C", "8,17,4,1", "Easy",  "Guitar", "1", 2);
            insertLesson(sqLiteDatabase, 2002, "Chords:\nE, A, Dm, G", "7,16,5,13", "Easy",  "Guitar", "1", 2);
            insertLesson(sqLiteDatabase, 2003, "Chords:\nE7, D7, A7, G7", "9,6,18,15", "Easy",  "Guitar", "1", 2);
            insertLesson(sqLiteDatabase, 2004, "Chords:\nF7, C7, H7, B7", "12,3,24,21", "Medium",  "Guitar", "1", 2);
            insertLesson(sqLiteDatabase, 2005, "Chords:\nCm, H, Bm, F", "2,22,20,10", "Medium",  "Guitar", "1", 2);
            insertLesson(sqLiteDatabase, 2006, "Chords:\nHm, Gm, Fm, B", "14,23,19,14", "Medium",  "Guitar", "1", 2);

            insertLesson(sqLiteDatabase, 3001, "Names:\nEm, Am, D, C", "8,17,4,1", "Easy",  "Guitar", "1", 3);
            insertLesson(sqLiteDatabase, 3002, "Names:\nE, A, Dm, G", "7,16,5,13", "Easy",  "Guitar", "1", 3);
            insertLesson(sqLiteDatabase, 3003, "Names:\nE7, D7, A7, G7", "9,6,18,15", "Easy",  "Guitar", "1", 3);
            insertLesson(sqLiteDatabase, 3004, "Names:\nF7, C7, H7, B7", "12,3,24,21", "Medium",  "Guitar", "1", 3);
            insertLesson(sqLiteDatabase, 3005, "Names:\nCm, H, Bm, F", "2,22,20,10", "Medium",  "Guitar", "1", 3);
            insertLesson(sqLiteDatabase, 3006, "Names:\nHm, Gm, Fm, B", "14,23,19,14", "Medium",  "Guitar", "1", 3);

            insertLesson(sqLiteDatabase, 1101, "Play:\nC, D, G, A", "101,104,113,116", "Easy",  "Ukulele", "2", 1);
            insertLesson(sqLiteDatabase, 1102, "Play:\nDm, Em, F, Am", "105,108,110,117", "Easy",  "Ukulele", "2", 1);
            insertLesson(sqLiteDatabase, 1103, "Play:\nG7, C7, A7, E7", "115,103,118,109", "Easy",  "Ukulele", "2", 1);
            insertLesson(sqLiteDatabase, 1104, "Play:\nCm, E, Fm, Gm", "102,107,111,114", "Medium",  "Ukulele", "2", 1);
            insertLesson(sqLiteDatabase, 1105, "Play:\nB, B7, Bm, D7", "119,121,120,106", "Medium",  "Ukulele", "2", 1);
            insertLesson(sqLiteDatabase, 1106, "Play:\nH, H7, Hm, F7", "122,124,123,112", "Medium",  "Ukulele", "2", 1);

            insertLesson(sqLiteDatabase, 2101, "Chords:\nC, D, G, A", "101,104,113,116", "Easy",  "Ukulele", "2", 2);
            insertLesson(sqLiteDatabase, 2102, "Chords:\nDm, Em, F, Am", "105,108,110,117", "Easy",  "Ukulele", "2", 2);
            insertLesson(sqLiteDatabase, 2103, "Chords:\nG7, C7, A7, E7", "115,103,118,109", "Easy",  "Ukulele", "2", 2);
            insertLesson(sqLiteDatabase, 2104, "Chords:\nCm, E, Fm, Gm", "102,107,111,114", "Medium",  "Ukulele", "2", 2);
            insertLesson(sqLiteDatabase, 2105, "Chords:\nB, B7, Bm, D7", "119,121,120,106", "Medium",  "Ukulele", "2", 2);
            insertLesson(sqLiteDatabase, 2106, "Chords:\nH, H7, Hm, F7", "122,124,123,112", "Medium",  "Ukulele", "2", 2);

            insertLesson(sqLiteDatabase, 3101, "Names:\nC, D, G, A", "101,104,113,116", "Easy",  "Ukulele", "2", 3);
            insertLesson(sqLiteDatabase, 3102, "Names:\nDm, Em, F, Am", "105,108,110,117", "Easy",  "Ukulele", "2", 3);
            insertLesson(sqLiteDatabase, 3103, "Names:\nG7, C7, A7, E7", "115,103,118,109", "Easy",  "Ukulele", "2", 3);
            insertLesson(sqLiteDatabase, 3104, "Names:\nCm, E, Fm, Gm", "102,107,111,114", "Medium",  "Ukulele", "2", 3);
            insertLesson(sqLiteDatabase, 3105, "Names:\nB, B7, Bm, D7", "119,121,120,106", "Medium",  "Ukulele", "2", 3);
            insertLesson(sqLiteDatabase, 3106, "Names:\nH, H7, Hm, F7", "122,124,123,112", "Medium",  "Ukulele", "2", 3);


            sqlDB = "CREATE TABLE lessontuning (id INTEGER PRIMARY KEY, frequences_array VARCHAR, instrument_id INTEGER, FOREIGN KEY(instrument_id) REFERENCES instrument(id))";
            sqLiteDatabase.execSQL(sqlDB);
            insertLessonTuning(sqLiteDatabase, 1, "82.41,110.00,146.83,196.00,246.94,329.63", "Guitar");
            insertLessonTuning(sqLiteDatabase, 2, "392.00,261.63,329.63,440.00", "Ukulele");

            sqlDB = "CREATE TABLE lessonchord (id INTEGER PRIMARY KEY, tone VARCHAR, type VARCHAR, tabs)";
            sqLiteDatabase.execSQL(sqlDB);
            insertLessonChord(sqLiteDatabase, 1, "C", "-dur", "0,3,2,0,1,0");
            insertLessonChord(sqLiteDatabase, 2, "C", "-moll", "-1,3,1,0,1,-1");
            insertLessonChord(sqLiteDatabase, 3, "C", "7", "0,3,2,3,1,0");
            insertLessonChord(sqLiteDatabase, 4, "D", "-dur", "-1,0,0,2,3,2");
            insertLessonChord(sqLiteDatabase, 5, "D", "-moll", "-1,0,0,2,3,1");
            insertLessonChord(sqLiteDatabase, 6, "D", "7", "-1,0,0,2,1,2");
            insertLessonChord(sqLiteDatabase, 7, "E", "-dur", "0,2,2,1,0,0");
            insertLessonChord(sqLiteDatabase, 8, "E", "-moll", "0,2,2,0,0,0");
            insertLessonChord(sqLiteDatabase, 9, "E", "7", "0,2,0,1,3,0");
            insertLessonChord(sqLiteDatabase, 10, "F", "-dur", "1,3,3,2,1,1");
            insertLessonChord(sqLiteDatabase, 11, "F", "-moll", "1,3,3,1,1,1");
            insertLessonChord(sqLiteDatabase, 12, "F", "7", "1,3,1,2,3,1");
            insertLessonChord(sqLiteDatabase, 13, "G", "-dur", "3,2,0,0,0,3");
            insertLessonChord(sqLiteDatabase, 14, "G", "-moll", "3,5,5,3,3,3");
            insertLessonChord(sqLiteDatabase, 15, "G", "7", "3,2,0,0,0,1");
            insertLessonChord(sqLiteDatabase, 16, "A", "-dur", "0,0,2,2,2,0");
            insertLessonChord(sqLiteDatabase, 17, "A", "-moll", "0,0,2,2,1,0");
            insertLessonChord(sqLiteDatabase, 18, "A", "7", "0,0,2,0,2,0");
            insertLessonChord(sqLiteDatabase, 19, "B", "-dur", "1,1,3,3,3,1");
            insertLessonChord(sqLiteDatabase, 20, "B", "-moll", "1,1,3,3,2,1");
            insertLessonChord(sqLiteDatabase, 21, "B", "7", "1,1,3,1,3,1");
            insertLessonChord(sqLiteDatabase, 22, "H", "-dur", "2,2,4,4,4,2");
            insertLessonChord(sqLiteDatabase, 23, "H", "-moll", "2,2,4,4,3,2");
            insertLessonChord(sqLiteDatabase, 24, "H", "7", "-1,2,1,2,0,2");

            insertLessonChord(sqLiteDatabase, 101, "C", "-dur", "0,0,0,3");
            insertLessonChord(sqLiteDatabase, 102, "C", "-moll", "0,3,3,3");
            insertLessonChord(sqLiteDatabase, 103, "C", "7", "0,0,0,1");
            insertLessonChord(sqLiteDatabase, 104, "D", "-dur", "2,2,2,0");
            insertLessonChord(sqLiteDatabase, 105, "D", "-moll", "2,2,1,0");
            insertLessonChord(sqLiteDatabase, 106, "D", "7", "2,2,2,3");
            insertLessonChord(sqLiteDatabase, 107, "E", "-dur", "1,4,0,2");
            insertLessonChord(sqLiteDatabase, 108, "E", "-moll", "0,4,3,2");
            insertLessonChord(sqLiteDatabase, 109, "E", "7", "1,2,0,2");
            insertLessonChord(sqLiteDatabase, 110, "F", "-dur", "2,0,1,0");
            insertLessonChord(sqLiteDatabase, 111, "F", "-moll", "1,0,1,3");
            insertLessonChord(sqLiteDatabase, 112, "F", "7", "2,3,1,3");
            insertLessonChord(sqLiteDatabase, 113, "G", "-dur", "0,2,3,2");
            insertLessonChord(sqLiteDatabase, 114, "G", "-moll", "0,2,3,1");
            insertLessonChord(sqLiteDatabase, 115, "G", "7", "0,2,1,2");
            insertLessonChord(sqLiteDatabase, 116, "A", "-dur", "2,1,0,0");
            insertLessonChord(sqLiteDatabase, 117, "A", "-moll", "2,0,0,0");
            insertLessonChord(sqLiteDatabase, 118, "A", "7", "0,1,0,0");
            insertLessonChord(sqLiteDatabase, 119, "B", "-dur", "3,2,1,1");
            insertLessonChord(sqLiteDatabase, 120, "B", "-moll", "3,1,1,1");
            insertLessonChord(sqLiteDatabase, 121, "B", "7", "1,2,1,1");
            insertLessonChord(sqLiteDatabase, 122, "H", "-dur", "4,3,2,2");
            insertLessonChord(sqLiteDatabase, 123, "H", "-moll", "4,2,2,2");
            insertLessonChord(sqLiteDatabase, 124, "H", "7", "2,3,2,2");




            sqlDB ="CREATE TABLE chord (id INTEGER PRIMARY KEY AUTOINCREMENT, tone VARCHAR, type VARCHAR, tabs VARCHAR, is_main BOOLEAN, instrument_id INTEGER, FOREIGN KEY(instrument_id) REFERENCES instrument(id))";
            sqLiteDatabase.execSQL(sqlDB);
            //Guitar

            //C
            insertChord(sqLiteDatabase, "C", "-dur", "0,3,2,0,1,0", 1, "Guitar");
            insertChord(sqLiteDatabase, "C", "-dur", "0,3,5,5,5,0", 0, "Guitar");

            insertChord(sqLiteDatabase, "C", "-moll", "-1,3,1,0,1,-1", 1, "Guitar");
            insertChord(sqLiteDatabase, "C", "-moll", "0,3,5,5,4,3", 0, "Guitar");

            insertChord(sqLiteDatabase, "C", "7", "0,3,2,3,1,0", 1, "Guitar");
            insertChord(sqLiteDatabase, "C", "7", "0,3,5,3,5,3", 0, "Guitar");

            insertChord(sqLiteDatabase, "C", "-moll 7", "-1,3,1,3,1,0", 1, "Guitar");
            insertChord(sqLiteDatabase, "C", "-moll 7", "0,3,5,3,4,3", 0, "Guitar");

            //C#
            insertChord(sqLiteDatabase, "C#", "-dur", "0,4,3,1,2,1", 1, "Guitar");
            insertChord(sqLiteDatabase, "C#", "-dur", "4,4,6,6,6,4", 0, "Guitar");

            insertChord(sqLiteDatabase, "C#", "-moll", "4,4,6,6,5,4", 1, "Guitar");
            insertChord(sqLiteDatabase, "C#", "-moll", "9,11,11,9,9,9", 0, "Guitar");

            insertChord(sqLiteDatabase, "C#", "7", "-1,4,3,4,2,-1", 1, "Guitar");
            insertChord(sqLiteDatabase, "C#", "7", "4,4,6,4,6,4", 0, "Guitar");

            insertChord(sqLiteDatabase, "C#", "-moll 7", "4,4,2,4,2,0", 1, "Guitar");
            insertChord(sqLiteDatabase, "C#", "-moll 7", "4,4,6,4,5,4", 0, "Guitar");

            //D
            insertChord(sqLiteDatabase, "D", "-dur", "-1,0,0,2,3,2", 1, "Guitar");
            insertChord(sqLiteDatabase, "D", "-dur", "5,5,7,7,7,5", 0, "Guitar");

            insertChord(sqLiteDatabase, "D", "-moll", "-1,0,0,2,3,1", 1, "Guitar");
            insertChord(sqLiteDatabase, "D", "-moll", "5,5,7,7,6,5", 0, "Guitar");

            insertChord(sqLiteDatabase, "D", "7", "-1,0,0,2,1,2", 1, "Guitar");
            insertChord(sqLiteDatabase, "D", "7", "5,5,7,5,7,5", 0, "Guitar");

            insertChord(sqLiteDatabase, "D", "-moll 7", "-1,0,0,2,1,1", 1, "Guitar");
            insertChord(sqLiteDatabase, "D", "-moll 7", "5,5,7,5,6,5", 0, "Guitar");

            //D#
            insertChord(sqLiteDatabase, "D#", "-dur", "-1,6,5,3,4,3", 1, "Guitar");
            insertChord(sqLiteDatabase, "D#", "-dur", "6,6,8,8,8,6", 0, "Guitar");

            insertChord(sqLiteDatabase, "D#", "-moll", "-1,-1,4,3,4,2", 1, "Guitar");
            insertChord(sqLiteDatabase, "D#", "-moll", "6,6,8,8,7,6", 0, "Guitar");

            insertChord(sqLiteDatabase, "D#", "7", "-1,6,5,6,4,-1", 1, "Guitar");
            insertChord(sqLiteDatabase, "D#", "7", "6,6,8,6,8,6", 0, "Guitar");

            insertChord(sqLiteDatabase, "D#", "-moll 7", "-1,6,4,6,4,-1", 1, "Guitar");
            insertChord(sqLiteDatabase, "D#", "-moll 7", "6,6,8,6,4,6", 0, "Guitar");

            //E
            insertChord(sqLiteDatabase, "E", "-dur", "0,2,2,1,0,0", 1, "Guitar");
            insertChord(sqLiteDatabase, "E", "-dur", "0,7,6,4,5,0", 0, "Guitar");

            insertChord(sqLiteDatabase, "E", "-moll", "0,2,2,0,0,0", 1, "Guitar");
            insertChord(sqLiteDatabase, "E", "-moll", "0,-1,5,4,5,3", 0, "Guitar");

            insertChord(sqLiteDatabase, "E", "7", "0,2,0,1,3,0", 1, "Guitar");
            insertChord(sqLiteDatabase, "E", "7", "0,7,6,7,5,0", 0, "Guitar");

            insertChord(sqLiteDatabase, "E", "-moll 7", "0,2,0,0,3,0", 1, "Guitar");
            insertChord(sqLiteDatabase, "E", "-moll 7", "0,-1,5,4,3,0", 0, "Guitar");

            //F
            insertChord(sqLiteDatabase, "F", "-dur", "1,3,3,2,1,1", 1, "Guitar");
            insertChord(sqLiteDatabase, "F", "-dur", "0,8,7,5,6,5", 0, "Guitar");

            insertChord(sqLiteDatabase, "F", "-moll", "1,3,3,1,1,1", 1, "Guitar");
            insertChord(sqLiteDatabase, "F", "-moll", "-1,-1,3,5,6,4", 0, "Guitar");

            insertChord(sqLiteDatabase, "F", "7", "1,3,1,2,3,1", 1, "Guitar");
            insertChord(sqLiteDatabase, "F", "7", "-1,8,7,8,6,-1", 0, "Guitar");

            insertChord(sqLiteDatabase, "F", "-moll 7", "1,3,1,1,4,1", 1, "Guitar");
            insertChord(sqLiteDatabase, "F", "-moll 7", "-1,-1,3,5,4,4", 0, "Guitar");

            //F#
            insertChord(sqLiteDatabase, "F#", "-dur", "2,4,4,3,2,2", 1, "Guitar");
            insertChord(sqLiteDatabase, "F#", "-dur", "-1,9,8,6,7,6", 0, "Guitar");

            insertChord(sqLiteDatabase, "F#", "-moll", "2,4,4,2,2,2", 1, "Guitar");
            insertChord(sqLiteDatabase, "F#", "-moll", "-1,0,4,6,7,5", 0, "Guitar");

            insertChord(sqLiteDatabase, "F#", "7", "2,4,2,3,4,1", 1, "Guitar");
            insertChord(sqLiteDatabase, "F#", "7", "0,9,8,9,7,0", 0, "Guitar");

            insertChord(sqLiteDatabase, "F#", "-moll 7", "2,4,2,2,5,2", 1, "Guitar");
            insertChord(sqLiteDatabase, "F#", "-moll 7", "-1,0,4,6,5,5", 0, "Guitar");

            //G
            insertChord(sqLiteDatabase, "G", "-dur", "3,2,0,0,0,3", 1, "Guitar");
            insertChord(sqLiteDatabase, "G", "-dur", "3,5,5,4,3,3", 0, "Guitar");

            insertChord(sqLiteDatabase, "G", "-moll", "3,5,5,3,3,3", 1, "Guitar");
            insertChord(sqLiteDatabase, "G", "-moll", "-1,-1,5,7,8,6", 0, "Guitar");

            insertChord(sqLiteDatabase, "G", "7", "3,2,0,0,0,1", 1, "Guitar");
            insertChord(sqLiteDatabase, "G", "7", "3,5,3,4,5,3", 0, "Guitar");

            insertChord(sqLiteDatabase, "G", "-moll 7", "3,5,1,1,6,3", 1, "Guitar");
            insertChord(sqLiteDatabase, "G", "-moll 7", "-1,-1,5,7,6,6", 0, "Guitar");

            //G#
            insertChord(sqLiteDatabase, "G#", "-dur", "-1,-1,1,1,1,4", 1, "Guitar");
            insertChord(sqLiteDatabase, "G#", "-dur", "4,6,6,5,4,4", 0, "Guitar");

            insertChord(sqLiteDatabase, "G#", "-moll", "4,6,6,4,4,4", 1, "Guitar");
            insertChord(sqLiteDatabase, "G#", "-moll", "-1,-1,6,8,9,7", 0, "Guitar");

            insertChord(sqLiteDatabase, "G#", "7", "-1,-1,1,1,1,2", 1, "Guitar");
            insertChord(sqLiteDatabase, "G#", "7", "4,6,4,5,7,4", 0, "Guitar");

            insertChord(sqLiteDatabase, "G#", "-moll 7", "6,8,6,6,9,6", 1, "Guitar");
            insertChord(sqLiteDatabase, "G#", "-moll 7", "-1,-1,5,8,7,7", 0, "Guitar");

            //A
            insertChord(sqLiteDatabase, "A", "-dur", "0,0,2,2,2,0", 1, "Guitar");
            insertChord(sqLiteDatabase, "A", "-dur", "5,7,7,6,5,5", 0, "Guitar");

            insertChord(sqLiteDatabase, "A", "-moll", "0,0,2,2,1,0", 1, "Guitar");
            insertChord(sqLiteDatabase, "A", "-moll", "5,7,7,5,5,5", 0, "Guitar");

            insertChord(sqLiteDatabase, "A", "7", "0,0,2,0,2,0", 1, "Guitar");
            insertChord(sqLiteDatabase, "A", "7", "5,7,5,6,0,5", 0, "Guitar");

            insertChord(sqLiteDatabase, "A", "-moll 7", "0,0,2,0,1,0", 1, "Guitar");
            insertChord(sqLiteDatabase, "A", "-moll 7", "5,7,5,5,8,5", 0, "Guitar");

            //B
            insertChord(sqLiteDatabase, "B", "-dur", "1,1,3,3,3,1", 1, "Guitar");
            insertChord(sqLiteDatabase, "B", "-dur", "6,8,8,7,6,6", 0, "Guitar");

            insertChord(sqLiteDatabase, "B", "-moll", "1,1,3,3,2,1", 1, "Guitar");
            insertChord(sqLiteDatabase, "B", "-moll", "6,8,8,6,6,6", 0, "Guitar");

            insertChord(sqLiteDatabase, "B", "7", "1,1,3,1,3,1", 1, "Guitar");
            insertChord(sqLiteDatabase, "B", "7", "6,8,6,7,0,6", 0, "Guitar");

            insertChord(sqLiteDatabase, "B", "-moll 7", "1,1,3,1,2,1", 1, "Guitar");
            insertChord(sqLiteDatabase, "B", "-moll 7", "6,8,6,6,9,6", 0, "Guitar");

            //H
            insertChord(sqLiteDatabase, "H", "-dur", "2,2,4,4,4,2", 1, "Guitar");
            insertChord(sqLiteDatabase, "H", "-dur", "7,9,9,8,7,7", 0, "Guitar");

            insertChord(sqLiteDatabase, "H", "-moll", "2,2,4,4,3,2", 1, "Guitar");
            insertChord(sqLiteDatabase, "H", "-moll", "7,9,9,7,7,7", 0, "Guitar");

            insertChord(sqLiteDatabase, "H", "7", "-1,2,1,2,0,2", 1, "Guitar");
            insertChord(sqLiteDatabase, "H", "7", "7,9,7,8,0,7", 0, "Guitar");

            insertChord(sqLiteDatabase, "H", "-moll 7", "-1,2,0,2,0,2", 1, "Guitar");
            insertChord(sqLiteDatabase, "H", "-moll 7", "7,9,7,7,10,7", 0, "Guitar");

            //Ukulele
            //C
            insertChord(sqLiteDatabase, "C", "-dur", "0,0,0,3", 1, "Ukulele");
            insertChord(sqLiteDatabase, "C", "-dur", "0,4,3,3", 0, "Ukulele");

            insertChord(sqLiteDatabase, "C", "-moll", "0,3,3,3", 1, "Ukulele");
            insertChord(sqLiteDatabase, "C", "-moll", "5,3,3,3", 0, "Ukulele");

            insertChord(sqLiteDatabase, "C", "7", "1,0,0,0", 1, "Ukulele");
            insertChord(sqLiteDatabase, "C", "7", "3,4,3,3", 0, "Ukulele");

            insertChord(sqLiteDatabase, "C", "-moll 7", "3,3,3,3", 1, "Ukulele");
            insertChord(sqLiteDatabase, "C", "-moll 7", "0,0,6,6", 0, "Ukulele");

            //C#
            insertChord(sqLiteDatabase, "C#", "-dur", "1,1,1,4", 1, "Ukulele");
            insertChord(sqLiteDatabase, "C#", "-dur", "6,5,4,4", 0, "Ukulele");

            insertChord(sqLiteDatabase, "C#", "-moll", "1,1,0,4", 1, "Ukulele");
            insertChord(sqLiteDatabase, "C#", "-moll", "6,4,4,4", 0, "Ukulele");

            insertChord(sqLiteDatabase, "C#", "7", "1,1,1,2", 1, "Ukulele");
            insertChord(sqLiteDatabase, "C#", "7", "4,5,4,4", 0, "Ukulele");

            insertChord(sqLiteDatabase, "C#", "-moll 7", "4,4,4,4", 1, "Ukulele");
            insertChord(sqLiteDatabase, "C#", "-moll 7", "1,1,0,2", 0, "Ukulele");

            //D
            insertChord(sqLiteDatabase, "D", "-dur", "2,2,2,0", 1, "Ukulele");
            insertChord(sqLiteDatabase, "D", "-dur", "7,6,5,5", 0, "Ukulele");

            insertChord(sqLiteDatabase, "D", "-moll", "2,2,1,0", 1, "Ukulele");
            insertChord(sqLiteDatabase, "D", "-moll", "7,5,5,5", 0, "Ukulele");

            insertChord(sqLiteDatabase, "D", "7", "2,2,2,3", 1, "Ukulele");
            insertChord(sqLiteDatabase, "D", "7", "5,6,5,5", 0, "Ukulele");

            insertChord(sqLiteDatabase, "D", "-moll 7", "2,2,1,3", 1, "Ukulele");
            insertChord(sqLiteDatabase, "D", "-moll 7", "5,5,5,5", 0, "Ukulele");

            //D#
            insertChord(sqLiteDatabase, "D#", "-dur", "0,3,3,1", 1, "Ukulele");
            insertChord(sqLiteDatabase, "D#", "-dur", "3,3,3,6", 0, "Ukulele");

            insertChord(sqLiteDatabase, "D#", "-moll", "3,3,2,1", 1, "Ukulele");
            insertChord(sqLiteDatabase, "D#", "-moll", "8,6,6,6", 0, "Ukulele");

            insertChord(sqLiteDatabase, "D#", "7", "3,3,3,4", 1, "Ukulele");
            insertChord(sqLiteDatabase, "D#", "7", "6,7,6,6", 0, "Ukulele");

            insertChord(sqLiteDatabase, "D#", "-moll 7", "3,3,2,4", 1, "Ukulele");
            insertChord(sqLiteDatabase, "D#", "-moll 7", "6,6,6,6", 0, "Ukulele");

            //E
            insertChord(sqLiteDatabase, "E", "-dur", "4,4,4,2", 1, "Ukulele");
            insertChord(sqLiteDatabase, "E", "-dur", "4,4,4,7", 0, "Ukulele");

            insertChord(sqLiteDatabase, "E", "-moll", "0,4,0,2", 1, "Ukulele");
            insertChord(sqLiteDatabase, "E", "-moll", "9,7,7,7", 0, "Ukulele");

            insertChord(sqLiteDatabase, "E", "7", "1,2,0,2", 1, "Ukulele");
            insertChord(sqLiteDatabase, "E", "7", "4,4,4,5", 0, "Ukulele");

            insertChord(sqLiteDatabase, "E", "-moll 7", "0,2,0,2", 1, "Ukulele");
            insertChord(sqLiteDatabase, "E", "-moll 7", "7,7,7,7", 0, "Ukulele");

            //F
            insertChord(sqLiteDatabase, "F", "-dur", "2,0,1,0", 1, "Ukulele");
            insertChord(sqLiteDatabase, "F", "-dur", "5,5,5,3", 0, "Ukulele");

            insertChord(sqLiteDatabase, "F", "-moll", "1,0,1,3", 1, "Ukulele");
            insertChord(sqLiteDatabase, "F", "-moll", "5,5,4,3", 0, "Ukulele");

            insertChord(sqLiteDatabase, "F", "7", "2,3,1,3", 1, "Ukulele");
            insertChord(sqLiteDatabase, "F", "7", "5,5,5,6", 0, "Ukulele");

            insertChord(sqLiteDatabase, "F", "-moll 7", "1,3,1,3", 1, "Ukulele");
            insertChord(sqLiteDatabase, "F", "-moll 7", "8,8,8,8", 0, "Ukulele");

            //F#
            insertChord(sqLiteDatabase, "F#", "-dur", "3,1,2,1", 1, "Ukulele");
            insertChord(sqLiteDatabase, "F#", "-dur", "6,6,6,4", 0, "Ukulele");

            insertChord(sqLiteDatabase, "F#", "-moll", "2,1,2,0", 1, "Ukulele");
            insertChord(sqLiteDatabase, "F#", "-moll", "6,6,5,4", 0, "Ukulele");

            insertChord(sqLiteDatabase, "F#", "7", "3,4,2,4", 1, "Ukulele");
            insertChord(sqLiteDatabase, "F#", "7", "6,6,6,7", 0, "Ukulele");

            insertChord(sqLiteDatabase, "F#", "-moll 7", "2,4,2,4", 1, "Ukulele");
            insertChord(sqLiteDatabase, "F#", "-moll 7", "6,6,0,0", 0, "Ukulele");

            //G
            insertChord(sqLiteDatabase, "G", "-dur", "0,2,3,2", 1, "Ukulele");
            insertChord(sqLiteDatabase, "G", "-dur", "0,7,7,5", 0, "Ukulele");

            insertChord(sqLiteDatabase, "G", "-moll", "0,2,3,1", 1, "Ukulele");
            insertChord(sqLiteDatabase, "G", "-moll", "7,7,6,5", 0, "Ukulele");

            insertChord(sqLiteDatabase, "G", "7", "0,2,1,2", 1, "Ukulele");
            insertChord(sqLiteDatabase, "G", "7", "7,7,7,8", 0, "Ukulele");

            insertChord(sqLiteDatabase, "G", "-moll 7", "0,2,1,1", 1, "Ukulele");
            insertChord(sqLiteDatabase, "G", "-moll 7", "3,5,3,5", 0, "Ukulele");

            //G#
            insertChord(sqLiteDatabase, "G#", "-dur", "5,3,4,3", 1, "Ukulele");
            insertChord(sqLiteDatabase, "G#", "-dur", "8,8,8,6", 0, "Ukulele");

            insertChord(sqLiteDatabase, "G#", "-moll", "4,3,4,2", 1, "Ukulele");
            insertChord(sqLiteDatabase, "G#", "-moll", "8,8,7,6", 0, "Ukulele");

            insertChord(sqLiteDatabase, "G#", "7", "1,3,2,3", 1, "Ukulele");
            insertChord(sqLiteDatabase, "G#", "7", "5,6,4,6", 0, "Ukulele");

            insertChord(sqLiteDatabase, "G#", "-moll 7", "4,6,4,6", 1, "Ukulele");
            insertChord(sqLiteDatabase, "G#", "-moll 7", "1,3,2,2", 0, "Ukulele");

            //A
            insertChord(sqLiteDatabase, "A", "-dur", "2,1,0,0", 1, "Ukulele");
            insertChord(sqLiteDatabase, "A", "-dur", "6,4,5,4", 0, "Ukulele");

            insertChord(sqLiteDatabase, "A", "-moll", "2,0,0,0", 1, "Ukulele");
            insertChord(sqLiteDatabase, "A", "-moll", "5,4,5,3", 0, "Ukulele");

            insertChord(sqLiteDatabase, "A", "7", "0,1,0,0", 1, "Ukulele");
            insertChord(sqLiteDatabase, "A", "7", "2,4,3,4", 0, "Ukulele");

            insertChord(sqLiteDatabase, "A", "-moll 7", "0,0,0,0", 1, "Ukulele");
            insertChord(sqLiteDatabase, "A", "-moll 7", "5,7,5,7", 0, "Ukulele");

            //B
            insertChord(sqLiteDatabase, "B", "-dur", "3,2,1,1", 1, "Ukulele");
            insertChord(sqLiteDatabase, "B", "-dur", "7,5,6,5", 0, "Ukulele");

            insertChord(sqLiteDatabase, "B", "-moll", "3,1,1,1", 1, "Ukulele");
            insertChord(sqLiteDatabase, "B", "-moll", "6,5,6,4", 0, "Ukulele");

            insertChord(sqLiteDatabase, "B", "7", "1,2,1,1", 1, "Ukulele");
            insertChord(sqLiteDatabase, "B", "7", "3,5,4,5", 0, "Ukulele");

            insertChord(sqLiteDatabase, "B", "-moll 7", "1,1,1,1", 1, "Ukulele");
            insertChord(sqLiteDatabase, "B", "-moll 7", "6,8,6,8", 0, "Ukulele");

            //H
            insertChord(sqLiteDatabase, "H", "-dur", "4,3,2,2", 1, "Ukulele");
            insertChord(sqLiteDatabase, "H", "-dur", "8,6,7,6", 0, "Ukulele");

            insertChord(sqLiteDatabase, "H", "-moll", "4,2,2,2", 1, "Ukulele");
            insertChord(sqLiteDatabase, "H", "-moll", "7,6,7,5", 0, "Ukulele");

            insertChord(sqLiteDatabase, "H", "7", "2,3,2,2", 1, "Ukulele");
            insertChord(sqLiteDatabase, "H", "7", "4,6,5,6", 0, "Ukulele");

            insertChord(sqLiteDatabase, "H", "-moll 7", "2,2,2,2", 1, "Ukulele");
            insertChord(sqLiteDatabase, "H", "-moll 7", "7,9,7,9", 0, "Ukulele");

        }
    }

    private static void insertInstrument(SQLiteDatabase db, String name, int stringsNumber, String image, String description){
        ContentValues instrumentValue = new ContentValues();
        instrumentValue.put("name", name);
        instrumentValue.put("strings_number", stringsNumber);
        instrumentValue.put("image", image);
        instrumentValue.put("description", description);
        db.insert("instrument", null, instrumentValue);
    }

    private static void insertChord(SQLiteDatabase db, String tone, String type, String tabs, int is_main, String instrument_name){
        ContentValues chordValue = new ContentValues();
        chordValue.put("tone", tone);
        chordValue.put("type", type);
        chordValue.put("tabs", tabs);
        chordValue.put("is_main", is_main);
        Cursor cursor = db.rawQuery("SELECT id FROM instrument WHERE name=?", new String[]{String.valueOf(instrument_name)});
        int id = 0;
        if(cursor.moveToFirst()){
            id = cursor.getInt(cursor.getColumnIndex("id"));
        }
        cursor.close();
        chordValue.put("instrument_id", id);
        db.insert("chord", null, chordValue);
    }

    private static void insertLesson(SQLiteDatabase db, int id, String name, String chords_ids, String difficulty, String instrument_name, String lesson_tuning_id, int lesson_type){
        ContentValues lessonValue = new ContentValues();
        lessonValue.put("id", id);
        lessonValue.put("name", name);
        lessonValue.put("chords_ids", chords_ids);
        lessonValue.put("difficulty", difficulty);

        Cursor cursor = db.rawQuery("SELECT id FROM instrument WHERE name=?", new String[]{String.valueOf(instrument_name)});
        int instrument_id = 0;
        if(cursor.moveToFirst()){
            instrument_id = cursor.getInt(cursor.getColumnIndex("id"));
        }
        cursor.close();
        lessonValue.put("instrument_id", instrument_id);
        lessonValue.put("lesson_tuning_id", lesson_tuning_id);
        lessonValue.put("lesson_type", lesson_type);
        db.insert("lesson", null, lessonValue);
    }

    private static void insertLessonTuning(SQLiteDatabase db, int id, String frequences_array, String instrument_name){
        ContentValues lessonTuningValue = new ContentValues();
        lessonTuningValue.put("id", id);
        lessonTuningValue.put("frequences_array", frequences_array);
        Cursor cursor = db.rawQuery("SELECT id FROM instrument WHERE name=?", new String[]{String.valueOf(instrument_name)});
        int instrument_id = 0;
        if(cursor.moveToFirst()){
            instrument_id = cursor.getInt(cursor.getColumnIndex("id"));
        }
        cursor.close();
        lessonTuningValue.put("instrument_id", instrument_id);
        db.insert("lessontuning", null, lessonTuningValue);
    }

    private static void insertLessonChord(SQLiteDatabase db, int id, String tone, String type, String tabs){
        ContentValues lessonChord = new ContentValues();
        lessonChord.put("id", id);
        lessonChord.put("tone", tone);
        lessonChord.put("type", type);
        lessonChord.put("tabs", tabs);
        db.insert("lessonchord", null, lessonChord);
    }

    private static void insertTuning(SQLiteDatabase db, String name, String pitch_array, String instrument_name){
        ContentValues tuningValue = new ContentValues();
        tuningValue.put("name", name);
        tuningValue.put("pitch_array", pitch_array);
        Cursor cursor = db.rawQuery("SELECT id FROM instrument WHERE name=?", new String[]{String.valueOf(instrument_name)});
        int id = 0;
        if(cursor.moveToFirst()){
            id = cursor.getInt(cursor.getColumnIndex("id"));
        }
        cursor.close();
        tuningValue.put("instrument_id", id);
        db.insert("tuning", null, tuningValue);
    }
}
