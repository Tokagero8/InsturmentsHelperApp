package com.example.insturmentshelperapp.view.lessons;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.insturmentshelperapp.R;
import com.example.insturmentshelperapp.presenter.lessons.LessonsSettingsSelectInstrumentPresenter;

public class LessonsSettingsSelectInstrumentFragment extends Fragment implements LessonsSettingsSelectInstrumentInterface {

    LessonsSettingsSelectInstrumentPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lessons_settings_select_instrument, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        presenter = new LessonsSettingsSelectInstrumentPresenter(this, getActivity());
        ListView instrumentListView = getView().findViewById(R.id.list_view_select_lesson_instrument);
        LessonsSettingsSelectInstrumentAdapter adapter = new LessonsSettingsSelectInstrumentAdapter(
                getActivity(), presenter.getInstruments());
        instrumentListView.setAdapter(adapter);
        instrumentListView.setOnItemClickListener(instrumentClick);
    }


    private AdapterView.OnItemClickListener instrumentClick = (new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id){
            presenter.setInstrumentAndCloseFragment(adapterView.getItemAtPosition(position));
        }
    });

    @Override
    public void closeFragment() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

}