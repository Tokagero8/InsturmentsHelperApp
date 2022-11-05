package com.example.insturmentshelperapp.view.lessons;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.insturmentshelperapp.R;
import com.example.insturmentshelperapp.model.Lesson;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LessonsSettingsSelectLessonsAdapter extends BaseAdapter {
    private Context context;
    List<Lesson> items;
    public List<Boolean> checkedList;

    public LessonsSettingsSelectLessonsAdapter(Context context, List<Lesson> items){
        this.context = context;
        this.items = items;
        this.checkedList = Arrays.asList(new Boolean[items.size()]);
        Collections.fill(this.checkedList, false);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Lesson getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint({"ViewHolder", "InflateParams"}) View view = LayoutInflater.from(context).inflate(R.layout.item_lessons_reset, null);

        TextView lessonName = view.findViewById(R.id.text_view_play_name);
        TextView intensity = view.findViewById(R.id.text_view_intensity_multipler);
        ImageView imageProgress = view.findViewById(R.id.image_view_progress_icon);
        TextView bestScore = view.findViewById(R.id.text_view_best_score);
        CheckBox checkBox = view.findViewById(R.id.check_box_lessons_reset);

        lessonName.setText(getItem(position).getName());
        intensity.setText(getItem(position).getIntensity() + "x");
        int imageId;
        if(getItem(position).getProgress().equals("Completed")){
            imageId = context.getResources().getIdentifier("full_star", "drawable", context.getPackageName());
        }else{
            imageId = context.getResources().getIdentifier("empty_star", "drawable", context.getPackageName());
        }
        imageProgress.setImageResource(imageId);
        bestScore.setText(getItem(position).getBestScore());


        checkBox.setTag(position);
        checkBox.setChecked(false);
        checkBox.setOnCheckedChangeListener(checkListener);

        return view;
    }

    public Boolean getCheckForItem(int position){
        return checkedList.get(position);
    }

    CompoundButton.OnCheckedChangeListener checkListener = new CompoundButton.OnCheckedChangeListener(){
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            checkedList.set((Integer)compoundButton.getTag(), isChecked);
        }
    };
}
