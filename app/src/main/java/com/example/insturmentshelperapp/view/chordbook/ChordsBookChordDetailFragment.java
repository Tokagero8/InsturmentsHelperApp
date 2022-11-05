package com.example.insturmentshelperapp.view.chordbook;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.insturmentshelperapp.R;
import com.example.insturmentshelperapp.model.Chord;
import com.example.insturmentshelperapp.presenter.chordbook.ChordsBookChordDetailPresenter;


public class ChordsBookChordDetailFragment extends Fragment implements ChordsBookChordDetailInterface {

    ChordsBookChordDetailPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chords_book_chord_detail, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        TextView chordTone = getView().findViewById(R.id.text_view_chord_tone);
        TextView chordType = getView().findViewById(R.id.text_view_chord_type);
        ImageView chordImage = getView().findViewById(R.id.image_view_chord);
        Bundle bundle = this.getArguments();
        if(bundle != null){
            Chord chord = (Chord)bundle.getSerializable("chord");
            chordTone.setText(chord.getTone());
            chordType.setText(chord.getType());
            presenter = new ChordsBookChordDetailPresenter(this, getActivity());
            chordImage.setImageBitmap(presenter.getBitmapForChord(chord, 340f));
        }
    }
}