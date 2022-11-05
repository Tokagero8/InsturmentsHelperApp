package com.example.insturmentshelperapp.view.chordbook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.insturmentshelperapp.R;

import java.util.ArrayList;

public class ChordsBookAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> items;

    public ChordsBookAdapter(Context context, ArrayList<String> items) {
        super();
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint({"ViewHolder", "InflateParams"}) View view = LayoutInflater.from(context).inflate(R.layout.item_chords_book_chord_name, null);

        String currentItem = (String) getItem(position);

        TextView textViewItemName = view.findViewById(R.id.text_view_chord_name);
        textViewItemName.setText(currentItem);
        return view;
    }
}
