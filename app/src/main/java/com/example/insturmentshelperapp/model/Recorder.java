package com.example.insturmentshelperapp.model;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

public class Recorder {

    static int RECORDER_AUDIO_SOURCE = MediaRecorder.AudioSource.MIC;
    static int RECORDER_SAMPLE = 16000;
    static int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_MONO;
    static int RECORDER_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    static int BUFFER_SIZE = 8192;
    static AudioRecord record;
    static int accurace = 5;

    public Recorder(){
        record = new AudioRecord(
                RECORDER_AUDIO_SOURCE,
                RECORDER_SAMPLE,
                RECORDER_CHANNELS,
                RECORDER_ENCODING,
                BUFFER_SIZE * 2);
    }

    public Recorder(int RECORDER_AUDIO_SOURCE, int RECORDER_SAMPLE, int RECORDER_CHANNELS, int RECORDER_ENCODING, int BUFFER_SIZE, int accurace){
        this.RECORDER_AUDIO_SOURCE = RECORDER_AUDIO_SOURCE;
        this.RECORDER_SAMPLE = RECORDER_SAMPLE;
        this.RECORDER_CHANNELS = RECORDER_CHANNELS;
        this.RECORDER_ENCODING = RECORDER_ENCODING;
        this.BUFFER_SIZE = BUFFER_SIZE;
        this.accurace = accurace;
        record = new AudioRecord(
                RECORDER_AUDIO_SOURCE,
                RECORDER_SAMPLE,
                RECORDER_CHANNELS,
                RECORDER_ENCODING,
                BUFFER_SIZE * 2);
    }

    public static int getRecorderAudioSource(){
        return RECORDER_AUDIO_SOURCE;
    }

    public static int getRecorderSample(){
        return RECORDER_SAMPLE;
    }

    public static int getRecorderChannels(){
        return RECORDER_CHANNELS;
    }

    public static int getRecorderEncoding(){
        return RECORDER_ENCODING;
    }

    public static int getBufferSize(){
        return BUFFER_SIZE;
    }

    public static int getAccurace(){
        return accurace;
    }



    public void startRecording(){
        record.startRecording();
    }

    public void stopRecording(){
        record.stop();
        record.release();
    }

    public void read(short[] buffer){
        record.read(buffer, 0, buffer.length);
    }


    //static Recorder instance;
        /*public static Recorder getInstance(){
        if (instance == null){
            instance = new Recorder();
        }
        return instance;
    }*/
}
