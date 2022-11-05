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
import com.example.insturmentshelperapp.presenter.chordbook.ChordsBookChordsVariantsPresenter;

public class ChordsBookChordsVariantsFragment extends Fragment implements ChordsBookChordsVariantsInterface{

    ChordsBookChordsVariantsPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chords_book_chord_variants, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        TextView chordTone = getView().findViewById(R.id.text_view_chord_tone);
        TextView chordType = getView().findViewById(R.id.text_view_chords_type);
        Bundle bundle = this.getArguments();
        if(bundle != null){
            Chord chord = (Chord)bundle.getSerializable("chord");
            chordTone.setText(chord.getTone());
            chordType.setText(chord.getType());
            presenter = new ChordsBookChordsVariantsPresenter(this, getActivity());
            final GridView chordsList = getView().findViewById(R.id.grid_view_chords_variants);
            ChordsBookChordsVariantsAdapter chordsAdapter = new ChordsBookChordsVariantsAdapter(
                    getActivity(), presenter.getChordsVariantsForChord(chord, 200f));
            chordsList.setAdapter(chordsAdapter);
            chordsList.setOnItemClickListener(chordClick);
        }
    }

    private AdapterView.OnItemClickListener chordClick = (new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            presenter.runChordFromPosition((Chord)adapterView.getItemAtPosition(position));
        }
    });

    public void runFragmentChord(Chord chord) {
        Fragment fragment = new ChordsBookChordDetailFragment();
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