package com.example.insturmentshelperapp.view.lessons;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.insturmentshelperapp.R;

import java.util.List;

public class LessonsAdapter extends BaseAdapter {
    private Context context;
    List<String> items;

    public LessonsAdapter(Context context, List<String> items){
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public String getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint({"ViewHolder", "InflateParams"}) View view = LayoutInflater.from(context).inflate(R.layout.item_lessons_main, null);

        TextView textView = view.findViewById(R.id.text_view_lessons_main);
        textView.setText(getItem(position));
        return view;
    }
}
