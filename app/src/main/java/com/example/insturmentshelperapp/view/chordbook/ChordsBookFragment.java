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

import com.example.insturmentshelperapp.R;
import com.example.insturmentshelperapp.presenter.chordbook.ChordsBookPresenter;

public class ChordsBookFragment extends Fragment implements ChordsBookInterface {

    ChordsBookPresenter presenter = new ChordsBookPresenter(this);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chords_book, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        GridView chordsGridView = getView().findViewById(R.id.grid_view_chords_book);
        ChordsBookAdapter chordsBookAdapter = new ChordsBookAdapter(this.getContext(), presenter.getToneNames());
        chordsGridView.setAdapter(chordsBookAdapter);
        chordsGridView.setOnItemClickListener(chordClick);
    }

    private AdapterView.OnItemClickListener chordClick = (new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            presenter.runToneFromPosition(position);
        }
    });

    public void runFragmentWithTone(String tone){
        Fragment fragment = new ChordsBookChordsPresentationFragment();
        Bundle bundle = new Bundle();
        bundle.putString("tone", tone);
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