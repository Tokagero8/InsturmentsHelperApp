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
import android.widget.ListView;

import com.example.insturmentshelperapp.R;
import com.example.insturmentshelperapp.model.Lesson;
import com.example.insturmentshelperapp.presenter.lessons.LessonsNamePresentationPresenter;


public class LessonsNamePresentationFragment extends Fragment implements LessonsNamePresentationInterface {

    LessonsNamePresentationPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lessons_name_presentation, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        presenter = new LessonsNamePresentationPresenter(this, getActivity());
        ListView lessonsList = getView().findViewById(R.id.list_view_lessons_name);
        LessonsPresentationAdapter lessonsAdapter = new LessonsPresentationAdapter(getActivity(), presenter.getLessons());
        lessonsList.setAdapter(lessonsAdapter);
        lessonsList.setOnItemClickListener(lessonClick);
    }

    private AdapterView.OnItemClickListener lessonClick = (new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id){
            presenter.runLesson(adapterView.getItemAtPosition(position));
        }
    });

    @Override
    public void runLesson(Lesson lesson) {
        Fragment fragment = new LessonsNameInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("lesson", lesson);
        fragment.setArguments(bundle);
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