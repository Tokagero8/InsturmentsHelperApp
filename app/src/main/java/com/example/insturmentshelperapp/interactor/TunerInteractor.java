package com.example.insturmentshelperapp.interactor;

import android.content.Context;

import com.example.insturmentshelperapp.model.Recorder;
import com.example.insturmentshelperapp.presenter.tuner.TunerPresenterInterface;

import org.jtransforms.fft.DoubleFFT_1D;


public class TunerInteractor {

    Context context;
    TunerPresenterInterface presenter;
    Thread audioThread;
    int correctCounter = 0;
    double lastButtonFrequence = -1;

    public TunerInteractor(Context context, TunerPresenterInterface presenter){
        this.context = context;
        this.presenter = presenter;
    }

    public void startListening(final Recorder recorder){
        recorder.startRecording();

        audioThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().interrupted()) {
                    TunerInteractor.this.writeAudioData(recorder);
                }
                TunerInteractor.this.stopAudioData(recorder);
            }
        }, "Audio Thread");
        audioThread.start();
    }

    public void stopListening(){
        audioThread.interrupt();
    }

    private void writeAudioData(Recorder recorder){
        short[] buffer = new short[Recorder.getBufferSize()];

        recorder.read(buffer);
        buffer = HannWindow(buffer);

        int amplitudeMax = 0;
        for (short amplitude : buffer) {
            if (Math.abs(amplitude) > amplitudeMax) {
                amplitudeMax = Math.abs(amplitude);
            }
        }
        System.out.println("Głośność: " + amplitudeMax);
        if(amplitudeMax > 350){
            calculateFrequence(buffer);
        }
    }

    private void calculateFrequence(short[] buffer){
        double[] bufferDoubleFit = new double[Recorder.getRecorderSample() * Recorder.getAccurace()];
        DoubleFFT_1D fftArray = new DoubleFFT_1D(Recorder.getRecorderSample()  * Recorder.getAccurace());
        double[] bufferDoubleFit2 = new double[(Recorder.getRecorderSample()  * Recorder.getAccurace()) / 2];
        DoubleFFT_1D fftArray2 = new DoubleFFT_1D((Recorder.getRecorderSample()  * Recorder.getAccurace()) / 2);

        double[] bufferDouble = short2double(buffer);

        System.arraycopy(bufferDouble, 0, bufferDoubleFit, 0, bufferDouble.length);
        System.arraycopy(bufferDouble, 0, bufferDoubleFit2, 0, bufferDouble.length);

        fftArray.realForward(bufferDoubleFit);
        fftArray2.realForward(bufferDoubleFit2);

        double[] finalArray = new double[buffer.length];
        for (int i = 0; i < finalArray.length; i++) {
            finalArray[i] = bufferDoubleFit[i] * bufferDoubleFit2[i];
        }

        final double frequence = (double) findFrequency(finalArray) / Recorder.getAccurace();

        System.out.println("Częstotliwość: " + frequence);

        double selectedButtonFrequence = presenter.getSelectedButtonFrequence();
        if(lastButtonFrequence != selectedButtonFrequence){
            lastButtonFrequence = selectedButtonFrequence;
            correctCounter = 0;
        }
        if(selectedButtonFrequence != -1){
            double cent = Math.round(1200 * Math.log((frequence) / selectedButtonFrequence ) / Math.log(2));
            if (cent > 10) {
                correctCounter = 0;
                presenter.tooHighTone(frequence, cent);
            } else if (cent < -10) {
                correctCounter = 0;
                presenter.tooLowTone(frequence, cent);
            } else {
                correctCounter++;
                presenter.CorrectTone(frequence, cent, correctCounter);
            }
        }
    }

    private short[] HannWindow(short[] buffer) {
        short[] HannBuffer = new short[buffer.length];
        for (int i = 0; i < buffer.length; i++) {
            HannBuffer[i] = (short) (buffer[i] * 0.5 * (1.0 - Math.cos(2 * Math.PI * i / (buffer.length - 1))));
        }
        return HannBuffer;
    }

    private double[] short2double(short[] sData) {
        double[] doubles = new double[sData.length];
        double d;
        for (int i = 0; i < doubles.length; i++) {
            d = ((double) sData[i]) / 32768.0;
            if (d > 1) d = 1;
            if (d < -1) d = -1;
            doubles[i] = d;
        }
        return doubles;
    }

    private int findFrequency(double[] signal) {
        double localMax = Double.MIN_VALUE;
        int maxFreqValue = 1;
        double[] result = new double[signal.length / 2];
        for (int s = 0; s < result.length; s++) {
            double re = signal[s * 2];
            double im = signal[s * 2 + 1];
            result[s] = Math.sqrt(re * re + im * im) / result.length;
            if (result[s] > localMax) {
                maxFreqValue = s;
            }
            localMax = Math.max(localMax, result[s]);
        }
        return maxFreqValue;
    }

    private void stopAudioData(Recorder recorder){
        try {
            audioThread.join();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        recorder.stopRecording();
    }
}
