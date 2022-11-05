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
import com.example.insturmentshelperapp.model.ChordWithBitmap;
import com.example.insturmentshelperapp.model.Lesson;
import com.example.insturmentshelperapp.presenter.lessons.LessonsChordPresenter;

import java.util.Collections;
import java.util.List;

public class LessonsChordFragment extends Fragment implements LessonsChordInterface {

    LessonsChordPresenter presenter;
    ChordWithBitmap currentChord;
    List<ChordWithBitmap> currentChordList;
    boolean isAnswered;
    boolean isTimeLimit = true;
    boolean isTimerStarted = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lessons_chord, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        presenter = new LessonsChordPresenter(this, getActivity());
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Lesson lesson = (Lesson) bundle.getSerializable("lesson");
            presenter.setTimer(lesson);
            presenter.setChords(lesson, 170f);
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
        TextView timer = getView().findViewById(R.id.text_view_chord_timer);
        if (timer != null) {
            int minutes = time / 60;
            int seconds = time % 60;
            if (seconds <= 9) {
                timer.setText(minutes + ":0" + seconds);
            } else {
                timer.setText(minutes + ":" + seconds);
            }
        }
        if (time == 0) {
            isTimeLimit = false;
        }
    }

    @Override
    public void setChords(List<ChordWithBitmap> chordsWithBitmapList) {
        currentChord = chordsWithBitmapList.get(0);
        setChordsInUI(chordsWithBitmapList);
    }

    @Override
    public void setNextChords(List<ChordWithBitmap> chordsWithBitmapList) {
        if (currentChord.getId() == chordsWithBitmapList.get(0).getId()) {
            currentChord = chordsWithBitmapList.get(1);
        } else {
            currentChord = chordsWithBitmapList.get(0);
        }
        setChordsInUI(chordsWithBitmapList);
    }

    @Override
    public void addPoint() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final TextView textViewScore = getView().findViewById(R.id.text_view_chord_score);
                int score = Integer.parseInt(textViewScore.getText().toString());
                final int finalScore = score + 1;
                textViewScore.setText(String.valueOf(finalScore));
            }
        });
    }

    @Override
    public void disableButtons() {
        ImageView chordView1 = getView().findViewById(R.id.image_view_chord_1);
        chordView1.setOnClickListener(null);
        ImageView chordView2 = getView().findViewById(R.id.image_view_chord_2);
        chordView2.setOnClickListener(null);
        ImageView chordView3 = getView().findViewById(R.id.image_view_chord_3);
        chordView3.setOnClickListener(null);
        ImageView chordView4 = getView().findViewById(R.id.image_view_chord_4);
        chordView4.setOnClickListener(null);
    }

    @Override
    public void runSummaryFragment() {
        Fragment fragment = new LessonsChordSummaryFragment();
        Bundle oldBundle = this.getArguments();
        Lesson lesson = (Lesson) oldBundle.getSerializable("lesson");
        Bundle bundle = new Bundle();
        bundle.putSerializable("lesson", lesson);
        TextView score = getView().findViewById(R.id.text_view_chord_score);
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

    private void setChordsInUI(final List<ChordWithBitmap> chordsWithBitmapList) {
        Collections.shuffle(chordsWithBitmapList);
        currentChordList = chordsWithBitmapList;
        isAnswered = false;


        getActivity().runOnUiThread(new Runnable() {
            final TextView currentChordName = getView().findViewById(R.id.text_view_chord_name);
            ImageView chordView1 = getView().findViewById(R.id.image_view_chord_1);
            ImageView chordView2 = getView().findViewById(R.id.image_view_chord_2);
            ImageView chordView3 = getView().findViewById(R.id.image_view_chord_3);
            ImageView chordView4 = getView().findViewById(R.id.image_view_chord_4);

            @Override
            public void run() {
                currentChordName.setText(currentChord.getTone() + currentChord.getType());

                chordView1.setBackgroundColor(getResources().getColor(R.color.chordNeutral));
                if(chordsWithBitmapList.size() >= 1){
                    chordView1.setImageBitmap(chordsWithBitmapList.get(0).getBitmap());
                    chordView1.setOnClickListener(imageClick1);
                }else{
                    chordView1.setImageResource(0);
                    chordView1.setOnClickListener(null);
                }

                chordView2.setBackgroundColor(getResources().getColor(R.color.chordNeutral));
                if(chordsWithBitmapList.size() >= 2){
                    chordView2.setImageBitmap(chordsWithBitmapList.get(1).getBitmap());
                    chordView2.setOnClickListener(imageClick2);
                }else{
                    chordView2.setImageResource(0);
                    chordView2.setOnClickListener(null);
                }

                chordView3.setBackgroundColor(getResources().getColor(R.color.chordNeutral));
                if (chordsWithBitmapList.size() >= 3) {
                    chordView3.setImageBitmap(chordsWithBitmapList.get(2).getBitmap());
                    chordView3.setOnClickListener(imageClick3);
                } else {
                    chordView3.setImageResource(0);
                    chordView3.setOnClickListener(null);
                }

                chordView4.setBackgroundColor(getResources().getColor(R.color.chordNeutral));
                if (chordsWithBitmapList.size() >= 4) {
                    chordView4.setImageBitmap(chordsWithBitmapList.get(3).getBitmap());
                    chordView4.setOnClickListener(imageClick4);
                } else {
                    chordView4.setImageResource(0);
                    chordView4.setOnClickListener(null);
                }
            }
        });

    }

    private ImageView.OnClickListener imageClick1 = (new ImageView.OnClickListener() {

        @Override
        public void onClick(View view) {
            view.setOnClickListener(null);
            if (currentChordList.get(0).equals(currentChord)) {
                setPositiveResult(view, isAnswered);
            } else {
                setNegativeResult(view);
            }
        }
    });

    private ImageView.OnClickListener imageClick2 = (new ImageView.OnClickListener() {
        @Override
        public void onClick(View view) {
            view.setOnClickListener(null);
            if (currentChordList.get(1).equals(currentChord)) {
                setPositiveResult(view, isAnswered);
            } else {
                setNegativeResult(view);
            }
        }
    });

    private ImageView.OnClickListener imageClick3 = (new ImageView.OnClickListener() {
        @Override
        public void onClick(View view) {
            view.setOnClickListener(null);
            if (currentChordList.get(2).equals(currentChord)) {
                setPositiveResult(view, isAnswered);
            } else {
                setNegativeResult(view);
            }
        }
    });

    private ImageView.OnClickListener imageClick4 = (new ImageView.OnClickListener() {
        @Override
        public void onClick(View view) {
            view.setOnClickListener(null);
            if (currentChordList.get(3).equals(currentChord)) {
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
                if (getActivity() != null) {
                    Bundle bundle = getArguments();
                    Lesson lesson = (Lesson) bundle.getSerializable("lesson");
                    if (!isAnswered) {
                        presenter.addPoint();
                    }
                    presenter.setNextChords(lesson, 170f);
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