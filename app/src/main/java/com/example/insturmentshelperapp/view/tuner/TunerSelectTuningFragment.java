package com.example.insturmentshelperapp.view.tuner;

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
import com.example.insturmentshelperapp.presenter.tuner.TunerSelectTuningPresenter;

public class TunerSelectTuningFragment extends Fragment implements TunerSelectTuningInterface {

    TunerSelectTuningPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tuner_select_tuning, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        presenter = new TunerSelectTuningPresenter(this, getActivity());
        ListView tuningListView = getView().findViewById(R.id.list_view_select_tuning);
        TunerSelectTuningAdapter selectTuningAdapter = new TunerSelectTuningAdapter(
                getActivity(), presenter.getTunings()
        );
        tuningListView.setAdapter(selectTuningAdapter);
        tuningListView.setOnItemClickListener(tuningClick);
    }

    private AdapterView.OnItemClickListener tuningClick = (new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id){
            presenter.setTuningAndRunTuner(adapterView.getItemAtPosition(position));
        }
    });

    public void runTuner(){
        Fragment fragment = new TunerFragment();

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }
}