package com.example.insturmentshelperapp.view;

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
import com.example.insturmentshelperapp.presenter.SelectInstrumentPresenter;
import com.example.insturmentshelperapp.view.chordbook.ChordsBookFragment;
import com.example.insturmentshelperapp.view.tuner.TunerFragment;


public class SelectInstrumentFragment extends Fragment implements SelectInstrumentInterface {

    SelectInstrumentPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_instrument, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        presenter = new SelectInstrumentPresenter(this, getActivity());
        ListView instrumentListView = getView().findViewById(R.id.list_view_select_instrument);
        SelectInstrumentAdapter selectInstrumentAdapter = new SelectInstrumentAdapter(
                getActivity(), presenter.getInstruments());
        instrumentListView.setAdapter(selectInstrumentAdapter);
        instrumentListView.setOnItemClickListener(instrumentClick);
    }

    private AdapterView.OnItemClickListener instrumentClick = (new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id){
            presenter.setInstrumentAndRunTuner(adapterView.getItemAtPosition(position));
        }
    });

    public void runTuner(){
        Fragment fragment;
        String simplename = getActivity().getClass().getSimpleName();
        switch(simplename){
            case "ChordsBookActivity":
                fragment = new ChordsBookFragment();
                break;
            default:
                fragment = new TunerFragment();
        }
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }
}