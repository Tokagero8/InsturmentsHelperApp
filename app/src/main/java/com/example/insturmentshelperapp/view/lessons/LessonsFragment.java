package com.example.insturmentshelperapp.view.lessons;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.insturmentshelperapp.R;
import com.example.insturmentshelperapp.presenter.lessons.LessonsPresenter;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


public class LessonsFragment extends Fragment implements LessonsInterface{

    LessonsPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lessons, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        presenter = new LessonsPresenter(this, getActivity());
        String[] lessons = {getString(R.string.play), getString(R.string.select_chord), getString(R.string.select_name)};
        GridView lessonsGridView = getView().findViewById(R.id.grid_view_lessons);
        LessonsAdapter lessonsAdapter = new LessonsAdapter(getActivity(), Arrays.asList(lessons));
        lessonsGridView.setAdapter(lessonsAdapter);
        lessonsGridView.setOnItemClickListener(lessonClick);
    }

    private AdapterView.OnItemClickListener lessonClick = (new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            presenter.runLesson(position);
        }
    });

    @Override
    public void runPlayLesson() {
        Fragment fragment = new LessonsPlayPresentationFragment();
        runFragment(fragment);
    }

    @Override
    public void runChordLesson() {
        Fragment fragment = new LessonsChordPresentationFragment();
        runFragment(fragment);
    }

    @Override
    public void runNameLesson() {
        Fragment fragment = new LessonsNamePresentationFragment();
        runFragment(fragment);
    }

    public void runFragment(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}