package com.example.insturmentshelperapp.view.chordbook;

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
import android.widget.TextView;

import com.example.insturmentshelperapp.R;
import com.example.insturmentshelperapp.model.Chord;
import com.example.insturmentshelperapp.presenter.chordbook.ChordsBookChordsPresentationPresenter;

public class ChordsBookChordsPresentationFragment extends Fragment implements ChordsBookChordsPresentationInterface {

    ChordsBookChordsPresentationPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chords_book_chords_presentation, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        TextView chordTone = getView().findViewById(R.id.text_view_chord_tone);
        Bundle bundle = this.getArguments();
        if(bundle != null){
            chordTone.setText(bundle.getString("tone"));
            presenter = new ChordsBookChordsPresentationPresenter(this, getActivity());
            final GridView chordsList = getView().findViewById(R.id.grid_view_chords_types);
            ChordsBookChordsPresentationAdapter chordsAdapter = new ChordsBookChordsPresentationAdapter(
                    getActivity(), presenter.getChordsForTone(bundle.getString("tone"), 200f));
            chordsList.setAdapter(chordsAdapter);
            chordsList.setOnItemClickListener(chordClick);
        }
    }

    private AdapterView.OnItemClickListener chordClick = (new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            presenter.runTypeFromPosition((Chord)adapterView.getItemAtPosition(position));
        }
    });

    public void runFragmentWithChordVariants(Chord chord){
        Fragment fragment = new ChordsBookChordsVariantsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("chord", chord);
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