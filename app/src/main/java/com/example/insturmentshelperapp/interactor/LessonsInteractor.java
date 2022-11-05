package com.example.insturmentshelperapp.interactor;

import android.content.Context;
import android.graphics.Color;

import com.example.insturmentshelperapp.database.AppDatabaseRepository;
import com.example.insturmentshelperapp.model.Chord;
import com.example.insturmentshelperapp.model.ChordWithBitmap;
import com.example.insturmentshelperapp.model.Lesson;
import com.example.insturmentshelperapp.model.Recorder;
import com.example.insturmentshelperapp.presenter.lessons.LessonsPlayPresenterInterface;

import org.jtransforms.fft.DoubleFFT_1D;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class LessonsInteractor {
    private AppDatabaseRepository appDatabaseRepository;
    private SharedPreferenceInteractor sharedPreferenceInteractor;
    private LessonsPlayPresenterInterface presenter;
    private ChordInteractor chordInteractor;
    List<String> frequenes_array;
    private Context context;
    Thread audioThread;

    public LessonsInteractor(Context context) {
        this.context = context;
        this.appDatabaseRepository = new AppDatabaseRepository(context);
        this.sharedPreferenceInteractor = new SharedPreferenceInteractor(context);
        this.chordInteractor = new ChordInteractor(context);
    }

    public LessonsInteractor(Context context, LessonsPlayPresenterInterface lessonsPlayPresenterInterface) {
        this.context = context;
        this.appDatabaseRepository = new AppDatabaseRepository(context);
        this.presenter = lessonsPlayPresenterInterface;
        this.sharedPreferenceInteractor = new SharedPreferenceInteractor(context);
        this.chordInteractor = new ChordInteractor(context);
        String frequences = appDatabaseRepository.getFrequencesForLessonTuning(presenter.getLessonTuningId());
        this.frequenes_array = getArrayFromString(frequences);
    }

    public List<Lesson> getLessons() {
        int lessonInstrumentId = sharedPreferenceInteractor.getSelectedLessonInstrumentsId();

        List<Lesson> lessonList = appDatabaseRepository.getLessonsForInstrument(lessonInstrumentId);

        if (lessonList.isEmpty()) {
            return new ArrayList<>();
        }

        return lessonList;
    }

    public List<Lesson> getLessonsWithType(int type) {
        int lessonInstrumentId = sharedPreferenceInteractor.getSelectedLessonInstrumentsId();

        List<Lesson> lessonList = appDatabaseRepository.getLessonsForInstrumentAndType(lessonInstrumentId, type);

        if (lessonList.isEmpty()) {
            return new ArrayList<>();
        }

        return lessonList;
    }

    public ChordWithBitmap getRandomChordWithBitmap(Lesson lesson, float targetHeight) {
        Chord chord = getRandomChord(lesson);
        return chordInteractor.getLessonChordWithBitmap(chord, targetHeight);
    }

    public Chord getRandomChord(Lesson lesson){
        List<String> chordsIdArray = getArrayFromString(lesson.getChords());
        Random rand = new Random();
        int randomNumber = rand.nextInt(chordsIdArray.size());
        Chord chord = appDatabaseRepository.getLessonChord(Integer.parseInt(chordsIdArray.get(randomNumber)));
        chord.setInstrumentId(lesson.getInstrumentId());
        return chord;
    }

    public List<ChordWithBitmap> getMultipleRandomChordsWithBitmap(Lesson lesson, float targetHeight, int number){
        List<Chord> chordList = getMultipleRandomChords(lesson, number);
        List<ChordWithBitmap> chordWithBitmapList = new ArrayList<>();
        for(int i = 0; i < chordList.size(); i++){
            chordWithBitmapList.add(chordInteractor.getLessonChordWithBitmap(chordList.get(i), targetHeight));
        }
        return chordWithBitmapList;
    }

    public List<Chord> getMultipleRandomChords(Lesson lesson, int number){
        List<String> chordsIdArray = getArrayFromString(lesson.getChords());
        Collections.shuffle(chordsIdArray);
        List<Chord> chordList = new ArrayList<>();
        if(chordsIdArray.size() < number){
            number = chordsIdArray.size();
        }
        for(int i = 0; i < number; i++){
            Chord chord = appDatabaseRepository.getLessonChord(Integer.parseInt(chordsIdArray.get(i)));
            chord.setInstrumentId(lesson.getInstrumentId());
            chordList.add(chord);
        }
        return chordList;
    }

    public ChordWithBitmap getRandomNextChordWithBitmap(Lesson lesson, float targetHeight, int currentChordId){
        Chord chord = getRandomNextChord(lesson, currentChordId);
        return chordInteractor.getLessonChordWithBitmap(chord, targetHeight);
    }

    public Chord getRandomNextChord(Lesson lesson, int currentChordId){
        List<String> chordsIdArray = getArrayFromString(lesson.getChords());
        Random rand = new Random();
        int randomNumber;
        boolean isNextChord = false;
        do{
            randomNumber = rand.nextInt(chordsIdArray.size());
            if(Integer.parseInt(chordsIdArray.get(randomNumber)) != currentChordId){
                isNextChord = true;
            }
        }while(!isNextChord);
        Chord chord = appDatabaseRepository.getLessonChord(Integer.parseInt(chordsIdArray.get(randomNumber)));
        chord.setInstrumentId(lesson.getInstrumentId());
        return chord;
    }

    private List<String> getArrayFromString(String frequences) {
        List<String> array = new ArrayList<>();

        AtomicInteger count = new AtomicInteger();
        AtomicInteger firstLetter = new AtomicInteger(-1);
        AtomicInteger secondLetter = new AtomicInteger();

        while (secondLetter.get() >= 0) {
            secondLetter.set(frequences.indexOf(",", firstLetter.get() + 1));
            if (secondLetter.get() >= 0) {
                array.add(frequences.substring(firstLetter.get() + 1, secondLetter.get()));
            } else {
                array.add(frequences.substring(firstLetter.get() + 1));
            }
            firstLetter.set(secondLetter.get());
            count.getAndIncrement();
        }
        return array;
    }

    public void resetIntensity(Lesson lesson){
        sharedPreferenceInteractor.setLessonIntensity(lesson.getId(), "1");
    }

    public void trySetBestScore(Lesson lesson, String bestScore){
        int lastBestScore = Integer.parseInt(sharedPreferenceInteractor.getLessonBestScore(lesson.getId()));
        if(Integer.parseInt(bestScore) > lastBestScore){
            sharedPreferenceInteractor.setLessonBestScore(lesson.getId(),bestScore);
        }
    }

    public void setLessonComplete(Lesson lesson){
        sharedPreferenceInteractor.setLessonProgress(lesson.getId(), "Completed");
    }

    public void increaseIntensity(Lesson lesson){
        BigDecimal lessonIntensity = new BigDecimal(sharedPreferenceInteractor.getLessonIntensity(lesson.getId()));
        if(lessonIntensity.compareTo(new BigDecimal("1.45")) < 0){
            sharedPreferenceInteractor.setLessonIntensity(lesson.getId(), lessonIntensity.add(new BigDecimal("0.15")).toString());
        }

    }

    public void startListening(final Recorder recorder) {
        recorder.startRecording();
        audioThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().interrupted()) {
                    LessonsInteractor.this.writeAudioData(recorder);
                }
                LessonsInteractor.this.stopAudioData(recorder);
            }
        }, "Audio Thread");
        audioThread.start();
    }

    private void writeAudioData(Recorder recorder) {
        short[] buffer = new short[recorder.getBufferSize()];

        recorder.read(buffer);
        buffer = HannWindow(buffer);

        int amplitudeMax = 0;
        for (short amplitude : buffer) {
            if (Math.abs(amplitude) > amplitudeMax) {
                amplitudeMax = Math.abs(amplitude);
            }
        }
        System.out.println("Głośność: " + amplitudeMax);
        if (amplitudeMax > 350) {
            calculateFrequence(buffer, recorder);
        }
    }

    private void calculateFrequence(short[] buffer, Recorder recorder) {
        double[] bufferDoubleFit = new double[recorder.getRecorderSample() * recorder.getAccurace()];
        DoubleFFT_1D fftArray = new DoubleFFT_1D(recorder.getRecorderSample() * recorder.getAccurace());
        double[] bufferDoubleFit2 = new double[(recorder.getRecorderSample() * recorder.getAccurace()) / 2];
        DoubleFFT_1D fftArray2 = new DoubleFFT_1D((recorder.getRecorderSample() * recorder.getAccurace()) / 2);

        double[] bufferDouble = short2double(buffer);

        System.arraycopy(bufferDouble, 0, bufferDoubleFit, 0, bufferDouble.length);
        System.arraycopy(bufferDouble, 0, bufferDoubleFit2, 0, bufferDouble.length);

        fftArray.realForward(bufferDoubleFit);
        fftArray2.realForward(bufferDoubleFit2);

        double[] finalArray = new double[buffer.length];
        for (int i = 0; i < finalArray.length; i++) {
            finalArray[i] = bufferDoubleFit[i] * bufferDoubleFit2[i];
        }
        if(!presenter.getIsChordChanging()){
            List<Integer> resultList = findFrequences(finalArray, recorder);
            presenter.setValidationResult(resultList);
        }
    }

    private List<Integer> findFrequences(double[] signal, Recorder recorder) {
        List<Integer> resultArray = new ArrayList<>();
        Chord selectedChord = presenter.getSelectedChord();
        String tabs = selectedChord.getTabs();
        List<String> tabsArray = getArrayFromString(tabs);
        List<Double> targetFrequences = getTargetFrequences(tabsArray, frequenes_array);


        double average = 0;
        AtomicInteger zeroCounter = new AtomicInteger();
        AtomicInteger aboveTreshold = new AtomicInteger();

        double[] result = new double[signal.length / 2];
        for (int s = 0; s < result.length; s++) {
            double re = signal[s * 2];
            double im = signal[s * 2 + 1];
            if((s / recorder.getAccurace() >= 440) && (s / recorder.getAccurace() <= 700)){
                result[s] = Math.sqrt(re + im) * s / recorder.getAccurace() / 50;
                average += Math.sqrt(re + im);
            }else{
                result[s] = Math.sqrt(re + im);
                average += result[s];
            }

            if (result[s] == 0) zeroCounter.getAndIncrement();
        }

        average /= (signal.length - zeroCounter.get());
        int threshold = (average > 1) ? (int) average : 1;
        System.out.println("Threshold: " + threshold);
        List<Double> frequencesAboveTreshold = new ArrayList<>();
        for (int s = 0; s < result.length; s++) {
            if (result[s] >= threshold) {
                aboveTreshold.getAndIncrement();
                System.out.print(s / recorder.getAccurace() + ", ");
                frequencesAboveTreshold.add(s / (double) recorder.getAccurace());
            }
        }

        for(Double frequence : targetFrequences){
            int findingResult = 0;
            if(frequence == -1.0){
                resultArray.add(1);

            }else{
                int upCent = (int) Math.round(Math.pow(2, 1.0 / 24.0) * frequence); // 50centów
                int downCent = (int) Math.round(Math.pow(Math.pow(2, 1.0 / 24.0) / frequence, -1.0));
                for(double frequenceAboveTreshold : frequencesAboveTreshold){
                    if (frequenceAboveTreshold > downCent && frequenceAboveTreshold < upCent){
                        findingResult = 1;
                        break;
                    }
                }
                resultArray.add(findingResult);
            }
        }

        System.out.println("\nAbove Treshold: " + aboveTreshold);
        //System.out.println("Wyniki sprawdzania: " + resultArray.get(0) + ", " + resultArray.get(1) + ", " + resultArray.get(2) + ", " + resultArray.get(3) + ", " + resultArray.get(4) + ", " + resultArray.get(5));
        //System.out.println(" ");


        return resultArray;
    }

    private List<Double> getTargetFrequences(List<String> tabs, List<String> frequences){
        List<Double> targetFrequences = new ArrayList<>();
        for(int i = 0; i < tabs.size(); i++){
            if(tabs.get(i).equals("-1")){
                targetFrequences.add(-1.0);
            }else{
                double frequence = Double.parseDouble(frequences.get(i));
                for(int c = Integer.parseInt(tabs.get(i)); c > 0; c--){
                    frequence *= 1.059463;
                }
                targetFrequences.add(frequence);
            }
        }
        return targetFrequences;
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

    public List<Integer> getColorsFromResults(List<List<Integer>> resultsList){
        List<Integer> colorList = new ArrayList<>();
        List<Integer> sumResultList = getResultsSum(resultsList);
        Integer sumResult = 0;
        for(Integer result : sumResultList){
            sumResult += result;
        }


        if(sumResult > sumResultList.size() * 2.55){
            System.out.println("Akord Zaliczony! Akord Zaliczony! Akord Zaliczony! Akord Zaliczony! Akord Zaliczony! Akord Zaliczony! Akord Zaliczony! Akord Zaliczony! ");
            for(Integer i : sumResultList){
                colorList.add(Color.GREEN);
            }
        }else{
            for(Integer i : sumResultList){
                switch(i){
                    case 0:
                        colorList.add(Color.RED);
                        break;
                    case 1:
                    case 2:
                        colorList.add(Color.YELLOW);
                        break;
                    case 3:
                        colorList.add(Color.GREEN);
                        break;
                    default:
                        colorList.add(Color.GRAY);
                        break;
                }
            }
        }
        return colorList;
    }

    List<Integer> getResultsSum(List<List<Integer>> resultsList){
        List<Integer> resultSumList = new ArrayList<>();
        int resultsListSize = resultsList.size();
        int listSize = resultsList.get(0).size();
        for(int i = 0; i < listSize; i++){
            Integer resultTemp = 0;
            for(int j = 0; j < resultsListSize; j++){
                resultTemp += resultsList.get(j).get(i);
            }
            resultSumList.add(resultTemp);
        }
        return resultSumList;
    }

    public void stopListening() {
        audioThread.interrupt();
    }

    private void stopAudioData(Recorder recorder) {
        try {
            audioThread.join();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        recorder.stopRecording();
    }
}
