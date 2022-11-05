package com.example.insturmentshelperapp.view.lessons;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.insturmentshelperapp.R;
import com.example.insturmentshelperapp.interactor.DateTimePickerInteractor;
import com.example.insturmentshelperapp.model.Lesson;
import com.example.insturmentshelperapp.presenter.lessons.LessonsNameSummaryPresenter;

import java.text.DateFormat;
import java.util.Calendar;

public class LessonsNameSummaryFragment extends Fragment implements LessonsNameSummaryInterface {

    LessonsNameSummaryPresenter presenter;
    boolean isPassed;
    String nextLessonName;
    Calendar nextLessonDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lessons_name_summary, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        presenter = new LessonsNameSummaryPresenter(this, getActivity());
        Bundle bundle = this.getArguments();
        if(bundle != null){
            Lesson lesson = (Lesson) bundle.getSerializable("lesson");
            String score = bundle.getString("score");
            presenter.setScores(lesson, score);
            presenter.setResult(lesson, score);
            presenter.setNextLesson(lesson, isPassed, score);
        }
        Button addLessonButton = getView().findViewById(R.id.button_name_add_lesson);
        if(addLessonButton != null){
            addLessonButton.setOnClickListener(addLessonButtonListener);
        }
        Button notAddLessonButton = getView().findViewById(R.id.button_name_dont_add_lesson);
        if(notAddLessonButton != null){
            notAddLessonButton.setOnClickListener(notAddLessonButtonListener);
        }
        Button manualAddLessonButton = getView().findViewById(R.id.button_name_add_lesson_manual);
        if(manualAddLessonButton != null){
            manualAddLessonButton.setOnClickListener(manualAddLessonButtonListener);
        }
        Button endLessonButton = getView().findViewById(R.id.button_end_name_lesson);
        endLessonButton.setOnClickListener(endLessonButtonListener);
    }

    @Override
    public void setScore(String score) {
        TextView scoreText = getView().findViewById(R.id.text_view_name_final_score);
        scoreText.setText(score);
    }

    @Override
    public void setNeededScore(int neededScore, double lessonIntensityMultipler) {
        TextView scoreText = getView().findViewById(R.id.text_view_name_needes_score);
        int finalNeededScore = (int) (neededScore * lessonIntensityMultipler);
        scoreText.setText(String.valueOf(finalNeededScore));
    }

    @Override
    public void setResult(int score, int neededScore, double lessonIntensityMultipler) {
        TextView resultText = getView().findViewById(R.id.text_view_name_result);
        int finalNeededScore = (int) (neededScore * lessonIntensityMultipler);
        if(score >= finalNeededScore){
            resultText.setText(getString(R.string.lesson_passed));
            isPassed = true;
        }else{
            resultText.setText(getString(R.string.lesson_failed));
            isPassed = false;
        }
    }

    @Override
    public void setNextLesson(Lesson lesson, int repeatability) {
        TextView nextLessonText = getView().findViewById(R.id.text_view_next_lesson_date);
        if(lesson == null){
            nextLessonText.setText(getString(R.string.complete_all_lesson));

            Button addButton = getView().findViewById(R.id.button_name_add_lesson);
            ViewGroup viewGroup = (ViewGroup) addButton.getParent();
            viewGroup.removeView(addButton);

            Button dontAddButton = getView().findViewById(R.id.button_name_dont_add_lesson);
            viewGroup = (ViewGroup) dontAddButton.getParent();
            viewGroup.removeView(dontAddButton);

            Button addManualyButton = getView().findViewById(R.id.button_name_add_lesson_manual);
            viewGroup = (ViewGroup) addManualyButton.getParent();
            viewGroup.removeView(addManualyButton);
        }else{
            String lessonName = lesson.getName();
            nextLessonName = lessonName;

            Calendar calendar = presenter.getPreferedDate(lesson, repeatability);

            nextLessonDate = calendar;

            String dateText = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());
            String timeText = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
            nextLessonText.setText(lessonName +  " " + getString(R.string.is_scheduled_for) + " " + dateText + " " + getString(R.string.at) + " " + timeText + getString(R.string.add_a_lesson_to_the_calendar));
        }
    }

    View.OnClickListener addLessonButtonListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            presenter.setNotification(nextLessonDate, nextLessonName);
            presenter.disableButtons();
        }
    };

    View.OnClickListener notAddLessonButtonListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            presenter.notSetNotification();
            presenter.disableButtons();
        }
    };

    View.OnClickListener manualAddLessonButtonListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            DialogFragment dateTimePicker = new DateTimePickerInteractor(nextLessonName, getActivity());
            dateTimePicker.show(getActivity().getSupportFragmentManager(), "dateTime picker");
            presenter.disableButtons();
        }
    };

    View.OnClickListener endLessonButtonListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            getActivity().getSupportFragmentManager().popBackStack();
        }
    };

    @Override
    public void disableButtons() {
        Button addLessonButton = getView().findViewById(R.id.button_name_add_lesson);
        addLessonButton.setEnabled(false);
        Button notAddLessonButton = getView().findViewById(R.id.button_name_dont_add_lesson);
        notAddLessonButton.setEnabled(false);
        Button manualAddLessonButton = getView().findViewById(R.id.button_name_add_lesson_manual);
        manualAddLessonButton.setEnabled(false);
    }
}