package com.example.insturmentshelperapp.interactor;

import android.content.Context;

import com.example.insturmentshelperapp.model.Recorder;
import com.example.insturmentshelperapp.presenter.lessons.LessonsPlayPresenterInterface;

public class ChordValidationInteractor {

    Context context;
    LessonsPlayPresenterInterface presenter;
    Thread audioThread;

    public ChordValidationInteractor(Context context, LessonsPlayPresenterInterface presenter){
        this.context = context;
        this.presenter = presenter;
    }

    public void startListening(final Recorder recorder){
        recorder.startRecording();
        audioThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().interrupted()) {
                    ChordValidationInteractor.this.writeAudioData(recorder);
                }
                ChordValidationInteractor.this.stopAudioData(recorder);
            }
        }, "Audio Thread");
        audioThread.start();
    }

    private void writeAudioData(Recorder recorder){

    }

    private void stopAudioData(Recorder recorder){

    }


}
