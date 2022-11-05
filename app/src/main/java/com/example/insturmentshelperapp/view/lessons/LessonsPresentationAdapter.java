package com.example.insturmentshelperapp.view.lessons;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.insturmentshelperapp.R;
import com.example.insturmentshelperapp.model.Lesson;

import java.util.List;

public class LessonsPresentationAdapter extends BaseAdapter {
    private Context context;
    List<Lesson> lessonList;

    public LessonsPresentationAdapter(Context context, List<Lesson> lessonList){
        this.context = context;
        this.lessonList = lessonList;
    }

    @Override
    public int getCount() {
        return lessonList.size();
    }

    @Override
    public Lesson getItem(int position) {
        return lessonList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint({"ViewHolder", "InflateParams"}) View view = LayoutInflater.from(context).inflate(R.layout.item_lessons, null);

        TextView lessonName = view.findViewById(R.id.text_view_play_name);
        TextView lessonDifficulty = view.findViewById(R.id.text_view_play_difficulty);
        ImageView imageProgress = view.findViewById(R.id.image_view_progress_icon);
        TextView bestScore = view.findViewById(R.id.text_view_best_score);

        lessonName.setText(getItem(position).getName());
        lessonDifficulty.setText(getItem(position).getDifficulty());
        int imageId;
        if(getItem(position).getProgress().equals("Completed")){
            imageId = context.getResources().getIdentifier("full_star", "drawable", context.getPackageName());
        }else{
            imageId = context.getResources().getIdentifier("empty_star", "drawable", context.getPackageName());
        }
        imageProgress.setImageResource(imageId);
        bestScore.setText(getItem(position).getBestScore());

        return view;
    }
}
