package com.example.insturmentshelperapp.view.lessons;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.insturmentshelperapp.R;
import com.example.insturmentshelperapp.model.Chord;
import com.example.insturmentshelperapp.model.ChordWithBitmap;
import com.example.insturmentshelperapp.model.Lesson;
import com.example.insturmentshelperapp.presenter.lessons.LessonsNamePresenter;

import java.util.Collections;
import java.util.List;


public class LessonsNameFragment extends Fragment implements LessonsNameInterface {

    LessonsNamePresenter presenter;
    ChordWithBitmap currentChord;
    List<Chord> currentChordList;
    boolean isAnswered;
    boolean isTimeLimit = true;
    boolean isTimerStarted = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lessons_name, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        presenter = new LessonsNamePresenter(this, getActivity());
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Lesson lesson = (Lesson) bundle.getSerializable("lesson");
            presenter.setTimer(lesson);
            presenter.setChords(lesson);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stopTimer();
        isTimerStarted = false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isTimerStarted = true;
        presenter.stopTimer();
    }

    @Override
    public void setNewTime(int time) {
        TextView timer = getView().findViewById(R.id.text_view_name_timer);
        if (timer != null) {
            int minutes = time / 60;
            int seconds = time % 60;
            if (seconds <= 9) {
                timer.setText(minutes + ":0" + seconds);
            } else {
                timer.setText(minutes + ":" + seconds);
            }
        }
        if(time == 0){
            isTimeLimit = false;
        }
    }

    @Override
    public void setChords(List<Chord> chordsList) {
        currentChord = presenter.getChordWithBitmap(chordsList.get(0), 252f);
        setChordsInUI(chordsList);
    }

    @Override
    public void setNextChords(List<Chord> chordList) {
        if (currentChord.getId() == chordList.get(0).getId()) {
            currentChord = presenter.getChordWithBitmap(chordList.get(1), 252f);
        } else {
            currentChord = presenter.getChordWithBitmap(chordList.get(0), 252f);
        }
        setChordsInUI(chordList);
    }

    @Override
    public void addPoint() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final TextView textViewScore = getView().findViewById(R.id.text_view_name_score);
                int score = Integer.parseInt(textViewScore.getText().toString());
                final int finalScore = score + 1;
                textViewScore.setText(String.valueOf(finalScore));
            }
        });
    }

    @Override
    public void disableButtons() {
        TextView chordView1 = getView().findViewById(R.id.text_view_chord_1);
        chordView1.setOnClickListener(null);
        TextView chordView2 = getView().findViewById(R.id.text_view_chord_2);
        chordView2.setOnClickListener(null);
        TextView chordView3 = getView().findViewById(R.id.text_view_chord_3);
        chordView3.setOnClickListener(null);
        TextView chordView4 = getView().findViewById(R.id.text_view_chord_4);
        chordView4.setOnClickListener(null);
    }

    @Override
    public void runSummaryFragment() {
        Fragment fragment = new LessonsNameSummaryFragment();
        Bundle oldBundle = this.getArguments();
        Lesson lesson = (Lesson) oldBundle.getSerializable("lesson");
        Bundle bundle = new Bundle();
        bundle.putSerializable("lesson", lesson);
        TextView score = getView().findViewById(R.id.text_view_name_score);
        bundle.putString("score", score.getText().toString());
        fragment.setArguments(bundle);
        runFragment(fragment);
    }

    private void runFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void setChordsInUI(final List<Chord> chordsList) {
        Collections.shuffle(chordsList);
        currentChordList = chordsList;
        isAnswered = false;


        getActivity().runOnUiThread(new Runnable() {
            final ImageView currentChordImage = getView().findViewById(R.id.image_view_chord);
            TextView chordView1 = getView().findViewById(R.id.text_view_chord_1);
            TextView chordView2 = getView().findViewById(R.id.text_view_chord_2);
            TextView chordView3 = getView().findViewById(R.id.text_view_chord_3);
            TextView chordView4 = getView().findViewById(R.id.text_view_chord_4);

            @Override
            public void run() {
                currentChordImage.setImageBitmap(currentChord.getBitmap());

                chordView1.setBackgroundColor(getResources().getColor(R.color.chordNeutral));
                if(chordsList.size() >= 1){
                    chordView1.setText(chordsList.get(0).getTone() + chordsList.get(0).getType());
                    chordView1.setOnClickListener(textClick1);
                }else{
                    chordView1.setText("");
                    chordView1.setOnClickListener(null);
                }

                chordView2.setBackgroundColor(getResources().getColor(R.color.chordNeutral));
                if(chordsList.size() >= 2){
                    chordView2.setText(chordsList.get(1).getTone() + chordsList.get(1).getType());
                    chordView2.setOnClickListener(textClick2);
                }else{
                    chordView2.setText("");
                    chordView2.setOnClickListener(null);
                }

                chordView3.setBackgroundColor(getResources().getColor(R.color.chordNeutral));
                if(chordsList.size() >= 3){
                    chordView3.setText(chordsList.get(2).getTone() + chordsList.get(2).getType());
                    chordView3.setOnClickListener(textClick3);
                }else{
                    chordView3.setText("");
                    chordView3.setOnClickListener(null);
                }

                chordView4.setBackgroundColor(getResources().getColor(R.color.chordNeutral));
                if(chordsList.size() >= 4){
                    chordView4.setText(chordsList.get(3).getTone() + chordsList.get(3).getType());
                    chordView4.setOnClickListener(textClick4);
                }else{
                    chordView4.setText("");
                    chordView4.setOnClickListener(null);
                }
            }
        });

    }

    private ImageView.OnClickListener textClick1 = (new ImageView.OnClickListener() {

        @Override
        public void onClick(View view) {
            view.setOnClickListener(null);
            if (currentChordList.get(0).getId() == currentChord.getId()) {
                setPositiveResult(view, isAnswered);
            } else {
                setNegativeResult(view);
            }
        }
    });

    private ImageView.OnClickListener textClick2 = (new ImageView.OnClickListener() {
        @Override
        public void onClick(View view) {
            view.setOnClickListener(null);
            if (currentChordList.get(1).getId() == currentChord.getId()) {
                setPositiveResult(view, isAnswered);
            } else {
                setNegativeResult(view);
            }
        }
    });

    private ImageView.OnClickListener textClick3 = (new ImageView.OnClickListener() {
        @Override
        public void onClick(View view) {
            view.setOnClickListener(null);
            if (currentChordList.get(2).getId() == currentChord.getId()) {
                setPositiveResult(view, isAnswered);
            } else {
                setNegativeResult(view);
            }
        }
    });

    private ImageView.OnClickListener textClick4 = (new ImageView.OnClickListener() {
        @Override
        public void onClick(View view) {
            view.setOnClickListener(null);
            if (currentChordList.get(3).getId() == currentChord.getId()) {
                setPositiveResult(view, isAnswered);
            } else {
                setNegativeResult(view);
            }
        }
    });

    private void setPositiveResult(final View view, final boolean isAnswered) {
        presenter.disableButtons();
        view.setBackgroundColor(getResources().getColor(R.color.chordPositive));
        final MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.bell_sound);
        mediaPlayer.start();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(getActivity() != null){
                    Bundle bundle = getArguments();
                    Lesson lesson = (Lesson) bundle.getSerializable("lesson");
                    if(!isAnswered){
                        presenter.addPoint();
                    }
                    presenter.setNextChords(lesson);
                    if (!isTimerStarted && isTimeLimit) {
                        presenter.startTimer();
                        isTimerStarted = true;
                    }
                }
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable, 400);

    }

    private void setNegativeResult(View view) {
        isAnswered = true;
        view.setBackgroundColor(getResources().getColor(R.color.chordNegative));
    }
}