package com.example.insturmentshelperapp.view.lessons;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.insturmentshelperapp.R;
import com.example.insturmentshelperapp.interactor.TimePickerInteractor;
import com.example.insturmentshelperapp.presenter.lessons.LessonsSettingsPresenter;

public class LessonsSettingsFragment extends Fragment implements LessonsSettingsInterface, TimePickerDialog.OnTimeSetListener {

    LessonsSettingsPresenter presenter;
    String[] lengthList;
    String[] repeatabilityList;
    Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lessons_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        presenter = new LessonsSettingsPresenter(this, getActivity());
        lengthList = new String[]{getString(R.string.none_minutes), getString(R.string.one_minute), getString(R.string.three_minutes), getString(R.string.five_minutes), getString(R.string.ten_minutes), getString(R.string.fifteen_minutes)};

        ImageView instrumentImageView = getView().findViewById(R.id.image_view_instrument_icon);
        instrumentImageView.setOnClickListener(selectInstrumentListener);

        TextView instrumentNameTextView = getView().findViewById(R.id.text_view_instrument_name);
        instrumentNameTextView.setOnClickListener(selectInstrumentListener);

        presenter.setSelectedInstrument();

        TextView lessonsLengthTextView = getView().findViewById(R.id.text_view_lessons_length_minutes);
        lessonsLengthTextView.setOnClickListener(selectPreferredLength);

        presenter.setSelectedPreferredLength();

        TextView lessonsTimeTextView = getView().findViewById(R.id.text_view_lessons_time_value);
        lessonsTimeTextView.setOnClickListener(selectPreferredTimeListener);

        presenter.setSelectedPreferredTime();

        TextView lessonsRepeatabilityTextView = getView().findViewById(R.id.text_view_lessons_repeatability_value);
        lessonsRepeatabilityTextView.setOnClickListener(selectPreferredRepeatabilityListener);

        presenter.setSelectedPreferredRepeatability();

        Button resetProgressButton = getView().findViewById(R.id.button_reset_progress);
        resetProgressButton.setOnClickListener(resetProgressButtonListener);

        Button resetBestScoreButton = getView().findViewById(R.id.button_reset_best_score);
        resetBestScoreButton.setOnClickListener(resetBestScoreButtonListener);

        Button resetIntensityButton = getView().findViewById(R.id.button_reset_intensity);
        resetIntensityButton.setOnClickListener(resetIntensityButtonListener);
    }

    @Override
    public void onStart() {
        super.onStart();
        Switch lessonsUseOfIntensitySwitch = getView().findViewById(R.id.switch_use_of_intensity);
        presenter.setSelectedPreferredUseOfIntensity();
        lessonsUseOfIntensitySwitch.setOnCheckedChangeListener(switchPreferredUseOfIntensityListener);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void setInstrumentName(String instrumentName) {
        TextView selectedInstrumentTextView = getView().findViewById(R.id.text_view_instrument_name);
        selectedInstrumentTextView.setText(instrumentName);
    }

    @Override
    public void setInstrumentImage(String instrumentImage) {
        int imageId = getActivity().getResources().getIdentifier(instrumentImage, "drawable", getActivity().getPackageName());
        ImageView instrumentImageView = getView().findViewById(R.id.image_view_instrument_icon);
        instrumentImageView.setImageResource(imageId);

    }

    @Override
    public void setSelectedPreferredLength(int intensityId) {
        TextView lessonsLengthTextView = getView().findViewById(R.id.text_view_lessons_length_minutes);
        lessonsLengthTextView.setText(lengthList[intensityId]);
    }

    @Override
    public void setSelectedPreferredTime(int hour, int minute) {
        TextView preferredTimeTextView = getView().findViewById(R.id.text_view_lessons_time_value);
        if (minute < 10) {
            preferredTimeTextView.setText(hour + ":0" + minute);
        } else {
            preferredTimeTextView.setText(hour + ":" + minute);
        }

    }

    @Override
    public void setSelectedPreferredRepeatability(int[] repeatabilityList, int repeatability) {
        TextView preferredRepeatabilityTextView = getView().findViewById(R.id.text_view_lessons_repeatability_value);
        this.repeatabilityList = getRepeatabilityStringList(repeatabilityList);
        preferredRepeatabilityTextView.setText(this.repeatabilityList[repeatability]);
    }

    @Override
    public void setSelectedPreferredUseOfIntensity(int useOfIntensity) {
        Switch lessonsUseOfIntensitySwitch = getView().findViewById(R.id.switch_use_of_intensity);
        lessonsUseOfIntensitySwitch.setChecked((useOfIntensity > 0));
    }

    @Override
    public void runInstrumentSelect() {
        Fragment fragment = new LessonsSettingsSelectInstrumentFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void runPreferredLengthDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Preferred Time");

        ListView selectListView = new ListView(getActivity());
        String[] stringArray = lengthList;
        ArrayAdapter<String> selectAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, stringArray);
        selectListView.setAdapter(selectAdapter);
        selectListView.setOnItemClickListener(selectLengthListener);

        builder.setView(selectListView);
        dialog = builder.create();
        dialog.show();
    }

    @Override
    public void selectPreferredTime() {
        DialogFragment timePicker = new TimePickerInteractor(this);
        timePicker.show(getActivity().getSupportFragmentManager(), "time picker");
    }

    @Override
    public void runPreferredRepeatabilityDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Preferred Repeatability");

        ListView selectListView = new ListView(getActivity());
        ArrayAdapter<String> selectAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, repeatabilityList);
        selectListView.setAdapter(selectAdapter);
        selectListView.setOnItemClickListener(selectRepeatabilityListener);

        builder.setView(selectListView);
        dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        presenter.setPreferredTime(hourOfDay, minute);
        presenter.setSelectedPreferredTime();
    }


    View.OnClickListener selectInstrumentListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            presenter.runInstrumentSelect();
        }
    };

    View.OnClickListener selectPreferredLength = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            presenter.runPreferredLengthDialog();
        }
    };

    ListView.OnItemClickListener selectLengthListener = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            presenter.setLessonsLength(position);
            TextView lessonsLengthTextView = getView().findViewById(R.id.text_view_lessons_length_minutes);
            lessonsLengthTextView.setText(lengthList[position]);
            dialog.dismiss();
        }
    };

    ListView.OnItemClickListener selectRepeatabilityListener = new ListView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            presenter.setLessonsRepeatability(position);
            TextView lessonsRepeatabilityTextView = getView().findViewById(R.id.text_view_lessons_repeatability_value);
            lessonsRepeatabilityTextView.setText(repeatabilityList[position]);
            dialog.dismiss();
        }
    };

    View.OnClickListener selectPreferredTimeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            presenter.selectPreferredTime();
        }
    };

    View.OnClickListener selectPreferredRepeatabilityListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            presenter.runPreferredRepeatabilityDialog();
        }
    };

    CompoundButton.OnCheckedChangeListener switchPreferredUseOfIntensityListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isTrue) {
            presenter.setPreferredUseOfIntensity(isTrue);
        }
    };

    View.OnClickListener resetProgressButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            runFragment("progress");
        }
    };

    View.OnClickListener resetBestScoreButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            runFragment("score");
        }
    };

    View.OnClickListener resetIntensityButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            runFragment("intensity");
        }
    };

    private String[] getRepeatabilityStringList(int[] repeatabilityList){
        String[] repeatabilityStringList = new String[repeatabilityList.length];
        for(int i = 0; i < repeatabilityList.length; i++){
            if(repeatabilityList[i] <= 0){
                repeatabilityStringList[i] = getString(R.string.none);
            }else if(repeatabilityList[i] == 1){
                repeatabilityStringList[i] = getString(R.string.every_day);
            }else{
                repeatabilityStringList[i] = getString(R.string.every) + " " + repeatabilityList[i] + " " + getString(R.string.days);
            }
        }
        return repeatabilityStringList;
    }

    private void runFragment(String resetType) {
        Fragment fragment = new LessonsSettingsSelectLessonsFragment();

        Bundle bundle = new Bundle();
        bundle.putString("reset", resetType);
        fragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}