package com.example.insturmentshelperapp.view.lessons;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.insturmentshelperapp.R;
import com.example.insturmentshelperapp.model.Chord;
import com.example.insturmentshelperapp.model.ChordWithBitmap;
import com.example.insturmentshelperapp.model.Lesson;
import com.example.insturmentshelperapp.presenter.lessons.LessonsPlayPresenter;

import java.util.ArrayList;
import java.util.List;

public class LessonsPlayFragment extends Fragment implements LessonsPlayInterface{

    LessonsPlayPresenter presenter;
    ChordWithBitmap currentChord;
    List<Integer> validationIdList = new ArrayList<>();
    List<List<Integer>> resultsList = new ArrayList<>();
    boolean isChordChanging = true;
    boolean isTimeLimit = true;
    boolean isTimerStarted = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lessons_play, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        presenter = new LessonsPlayPresenter(this, getActivity());
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Lesson lesson = (Lesson) bundle.getSerializable("lesson");
            presenter.setTimer(lesson);
            presenter.setChord(lesson, 340f);
            presenter.setValidationCircles(lesson);
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        presenter.startListening();
    }

    @Override
    public void onStop(){
        super.onStop();
        presenter.stopListening();
        presenter.stopTimer();
        isTimerStarted = false;
    }

    @Override
    public void setNewTime(int time) {
        TextView timer = getView().findViewById(R.id.text_view_play_timer);
        if(timer != null){
            int minutes = time / 60;
            int seconds = time % 60;
            if(seconds <= 9){
                timer.setText(minutes + ":0" + seconds);
            }else {
                timer.setText(minutes + ":" + seconds);
            }
        }
        if(time == 0){
            isTimeLimit = false;
        }
    }

    @Override
    public int getLessonTuningId() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Lesson lesson = (Lesson) bundle.getSerializable("lesson");
            return lesson.getLessonTuningId();
        } else{
            return -1;
        }
    }

    @Override
    public void setChord(final ChordWithBitmap chordWithBitmap) {
        currentChord = chordWithBitmap;

        getActivity().runOnUiThread(new Runnable() {
            ImageView chordView = getView().findViewById(R.id.image_view_chord_to_play);
            ChordWithBitmap chordWithBitmapI = chordWithBitmap;
            @Override
            public void run() {
                chordView.setImageBitmap(chordWithBitmapI.getBitmap());
            }
        });
        isChordChanging = false;
    }

    @Override
    public void setValidationCircles(int stringNumber) {
        LinearLayout linearLayout = getView().findViewById(R.id.linear_layout_play_lesson);
        linearLayout.removeAllViews();
        validationIdList.clear();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(40,0,40,0);
        for(int i = 0; i < stringNumber; i++){
            TextView textView = new TextView(getActivity());
            int id = View.generateViewId();
            validationIdList.add(id);
            textView.setId(id);
            textView.setText("⬤");
            textView.setTextSize(30);
            textView.setLayoutParams(params);
            linearLayout.addView(textView);
        }
    }

    @Override
    public boolean getIsChordChanging() {
        return isChordChanging;
    }

    @Override
    public Chord getSelectedChord() {
        return currentChord;
    }

    @Override
    public void setValidationResult(List<Integer> resultList) {
        if(resultsList.size() >= 3){
            resultsList.remove(0);
        }
        resultsList.add(resultList);
        if(resultsList.size() >= 3){
            presenter.drawResults(resultsList);
        }

    }

    @Override
    public void setResultColors(final List<Integer> colorList) {
        if(getActivity() != null){
            getActivity().runOnUiThread(new Runnable() {
                List<Integer> colorListI = colorList;
                @Override
                public void run() {
                    for(int i = 0; i < validationIdList.size(); i++){
                        TextView textView = getView().findViewById(validationIdList.get(i));
                        textView.setTextColor(colorListI.get(i));
                    }
                }
            });
            if(isAllResultsAreGreen(colorList)){
                isChordChanging = true;
                resultsList.clear();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.bell_sound);
                        mediaPlayer.start();
                        TextView textViewScore = getView().findViewById(R.id.text_view_play_score);
                        int score = Integer.parseInt(textViewScore.getText().toString());
                        score += 1;
                        textViewScore.setText(String.valueOf(score));
                    }
                });
                Bundle bundle = this.getArguments();
                Lesson lesson = (Lesson) bundle.getSerializable("lesson");
                presenter.setNextChord(lesson, 340f, currentChord.getId());
                if(!isTimerStarted && isTimeLimit){
                    presenter.startTimer();
                    isTimerStarted = true;
                }
            }
        }
    }

    private boolean isAllResultsAreGreen(List<Integer> colorList){
        for(int color : colorList){
            if(Color.GREEN != color){
                return false;
            }
        }
        return true;
    }

    @Override
    public void runSummaryFragment() {
        Fragment fragment = new LessonsPlaySummaryFragment();
        Bundle oldBundle = this.getArguments();
        Lesson lesson = (Lesson) oldBundle.getSerializable("lesson");
        Bundle bundle = new Bundle();
        bundle.putSerializable("lesson", lesson);
        TextView score = getView().findViewById(R.id.text_view_play_score);
        bundle.putString("score", score.getText().toString());
        fragment.setArguments(bundle);
        runFragment(fragment);
    }

    public void runFragment(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}