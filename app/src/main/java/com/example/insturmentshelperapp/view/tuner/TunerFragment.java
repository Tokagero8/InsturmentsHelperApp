package com.example.insturmentshelperapp.view.tuner;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.insturmentshelperapp.R;
import com.example.insturmentshelperapp.presenter.tuner.TunerPresenter;

public class TunerFragment extends Fragment implements TunerInterface {

    TunerPresenter presenter;
    TextView selectedInstrumentName;
    TextView selectedTuningName;
    RadioGroup stringsSelectLeft;
    RadioGroup stringsSelectRight;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tuner, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        presenter = new TunerPresenter(this, getActivity());
        selectedInstrumentName = getView().findViewById(R.id.text_view_selected_instrument_name);
        selectedTuningName = getView().findViewById(R.id.text_view_selected_tuning_name);
        stringsSelectLeft = getView().findViewById(R.id.radio_group_strings_select_left);
        stringsSelectRight = getView().findViewById(R.id.radio_group_strings_select_right);
        stringsSelectLeft.setOnCheckedChangeListener(leftCheck);
        stringsSelectRight.setOnCheckedChangeListener(rightCheck);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.setTextViewInstrumentName();
        presenter.setTextViewTuningName();
        presenter.setRadioButtons();
        presenter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stopListening();
    }

    @Override
    public void setSelectedInstrumentName(String instrumentName) {
        selectedInstrumentName.setText(instrumentName);
    }

    @Override
    public void setSelectedTuningName(String tuningName) {
        selectedTuningName.setText(tuningName);
    }

    @Override
    public void clearChecksLeft() {
        stringsSelectLeft.setOnCheckedChangeListener(null);
        stringsSelectLeft.clearCheck();
        stringsSelectLeft.setOnCheckedChangeListener(leftCheck);
    }

    @Override
    public void clearChecksRight() {
        stringsSelectRight.setOnCheckedChangeListener(null);
        stringsSelectRight.clearCheck();
        stringsSelectRight.setOnCheckedChangeListener(rightCheck);
    }

    @Override
    public void removeStringsSelectViews() {
        stringsSelectLeft.removeAllViews();
        stringsSelectRight.removeAllViews();
    }

    @Override
    public void addLeftButtonWithName(SpannableString name) {
        RadioButton radioButton = createRadioButtonWithName(name);
        stringsSelectLeft.addView(radioButton);
    }

    @Override
    public void addRightButtonWithName(SpannableString name) {
        RadioButton radioButton = createRadioButtonWithName(name);
        stringsSelectRight.addView(radioButton);
    }

    @Override
    public String getSelectedButtonPitchName() {
        int leftCheck = stringsSelectLeft.getCheckedRadioButtonId();
        int rightCheck = stringsSelectRight.getCheckedRadioButtonId();
        int selectedString = leftCheck == -1 ? rightCheck : leftCheck;

        try {
            RadioButton selectedStringButton = getView().findViewById(selectedString);
            if (selectedStringButton != null) {
                return selectedStringButton.getText().toString();
            } else {
                return "-1";
            }
        } catch (Exception e){
            return "-1";
        }

    }

    @Override
    public void setTuningInfo(String frequence, int color, double cent) {
        final String textFrequence = frequence;
        final int textColor = color;
        final String textCent = String.valueOf(cent);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView textViewFrequence = getView().findViewById(R.id.text_view_frequency_value);
                textViewFrequence.setText(textFrequence);
                textViewFrequence.setTextColor(textColor);

                TextView textViewCent = getView().findViewById(R.id.text_view_cents_value);
                textViewCent.setText(textCent);
            }
        });

    }

    @Override
    public void setButtonColor(int color) {
        int leftCheck = stringsSelectLeft.getCheckedRadioButtonId();
        int rightCheck = stringsSelectRight.getCheckedRadioButtonId();
        int selectedString = leftCheck == -1 ? rightCheck : leftCheck;

        RadioButton selectedStringButton = getView().findViewById(selectedString);
        if (selectedStringButton != null) {
            selectedStringButton.setTextColor(Color.GREEN);
        }
    }

    private RadioButton createRadioButtonWithName(SpannableString name) {
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMarginEnd(12);
        RadioButton radioButton = new RadioButton(getActivity());
        radioButton.setId(View.generateViewId());
        radioButton.setText(name);
        radioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 36);
        radioButton.setLayoutParams(params);
        return radioButton;
    }

    private RadioGroup.OnCheckedChangeListener leftCheck = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                presenter.clearChecksRight();
            }
        }
    };

    private RadioGroup.OnCheckedChangeListener rightCheck = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                presenter.clearChecksLeft();
            }
        }
    };


}