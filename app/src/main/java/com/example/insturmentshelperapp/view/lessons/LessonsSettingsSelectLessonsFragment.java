package com.example.insturmentshelperapp.view.lessons;

import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.insturmentshelperapp.R;
import com.example.insturmentshelperapp.model.Lesson;
import com.example.insturmentshelperapp.presenter.lessons.LessonsSettingsSelectLessonsPresenter;

import java.util.ArrayList;
import java.util.List;

public class LessonsSettingsSelectLessonsFragment extends Fragment implements LessonsSettingsSelectLessonsInterface {

    LessonsSettingsSelectLessonsPresenter presenter;
    LessonsSettingsSelectLessonsAdapter selectLessonsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lessons_settings_select_lessons, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        presenter = new LessonsSettingsSelectLessonsPresenter(this, getActivity());
        ListView lessonsListView = getView().findViewById(R.id.list_view_lessons_reset);
        selectLessonsAdapter = new LessonsSettingsSelectLessonsAdapter(
                getActivity(), presenter.getLessons());
        lessonsListView.setAdapter(selectLessonsAdapter);

        Button lessonsResetButton = getView().findViewById(R.id.button_lessons_reset);
        lessonsResetButton.setOnClickListener(resetButtonListener);
    }

    View.OnClickListener resetButtonListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            Bundle bundle = getArguments();
            if(bundle != null){
                String resetType = bundle.getString("reset");
                switch (resetType){
                    case "progress":
                        presenter.resetProgress();
                        break;
                    case "score":
                        presenter.resetScore();
                        break;
                    case "intensity":
                        presenter.resetIntensity();
                        break;
                }
            }
        }
    };

    @Override
    public void resetProgress() {
        List<Lesson> checkedLessonsList = getCheckedLessons();
        presenter.resetProgressForLessons(checkedLessonsList);
        presenter.closeFragment();
    }

    @Override
    public void resetScore() {
        List<Lesson> checkedLessonsList = getCheckedLessons();
        presenter.resetScoreForLessons(checkedLessonsList);
        presenter.closeFragment();
    }

    @Override
    public void resetIntensity() {
        List<Lesson> checkedLessonsList = getCheckedLessons();
        presenter.resetIntensityForLessons(checkedLessonsList);
        presenter.closeFragment();
    }

    @Override
    public void closeFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.commit();
    }

    public List<Lesson> getCheckedLessons(){
        List<Lesson> checkedLessonsList = new ArrayList<>();
        ListView lessonsListView = getView().findViewById(R.id.list_view_lessons_reset);
        for(int i = 0; i < lessonsListView.getAdapter().getCount(); i++){
            if(selectLessonsAdapter.getCheckForItem(i)){
                checkedLessonsList.add((Lesson) lessonsListView.getAdapter().getItem(i));
            }
        }
        return checkedLessonsList;
    }
}