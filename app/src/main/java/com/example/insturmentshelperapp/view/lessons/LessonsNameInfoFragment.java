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
import android.widget.GridView;
import android.widget.TextView;

import com.example.insturmentshelperapp.R;
import com.example.insturmentshelperapp.model.ChordWithBitmap;
import com.example.insturmentshelperapp.model.Lesson;
import com.example.insturmentshelperapp.presenter.lessons.LessonsNameInfoPresenter;

import java.util.List;

public class LessonsNameInfoFragment extends Fragment implements LessonsNameInfoInterface {

    LessonsNameInfoPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lessons_name_info, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        presenter = new LessonsNameInfoPresenter(this, getActivity());
        Bundle bundle = this.getArguments();
        if(bundle != null){
            Lesson lesson = (Lesson) bundle.getSerializable("lesson");
            presenter.setTimeAndPoints(lesson);
            presenter.setAdapter(lesson, 180f);
            presenter.setContinueButton(lesson);
        }
    }

    @Override
    public void setTimeAndPoints(int time, int points, double multipler) {
        TextView textViewInfo = getView().findViewById(R.id.text_view_lessons_name_info);
        String info1 = getString(R.string.name_info_1) + " ";
        String info2;
        String info3;
        if(time != -1){
            info2 = getString(R.string.info_2a) + " " + (int)(time*multipler)/60 + " " + getString(R.string.minutes) + " " + (int)(time*multipler)%60  + " " + getString(R.string.seconds) + " ";
        } else {
            info2 = getString(R.string.info_2b) + " ";
        }
        if(points != -1){
            info3 = getString(R.string.name_info_3a) + " " + (int)(points*multipler) + " " + getString(R.string.name_info_3b) + " ";
        } else {
            info3 = getString(R.string.info_3) + " ";
        }
        String info4 = getString(R.string.info_4);
        textViewInfo.setText(info1 + info2 + info3 + info4);
    }

    @Override
    public void setChordsAdapter(List<ChordWithBitmap> chords) {
        final GridView chordsGridView = getView().findViewById(R.id.grid_view_chords_info);
        LessonsInfoAdapter chordsAdapter = new LessonsInfoAdapter(getActivity(), chords);
        chordsGridView.setAdapter(chordsAdapter);
    }

    @Override
    public void setContinueButton(final Lesson lesson) {
        Button button = getView().findViewById(R.id.button_lesson_info_continute);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Fragment fragment = new LessonsNameFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("lesson", lesson);
                fragment.setArguments(bundle);
                runFragment(fragment);
            }
        });
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