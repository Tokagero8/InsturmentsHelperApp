package com.example.insturmentshelperapp.view.tuner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.insturmentshelperapp.R;
import com.example.insturmentshelperapp.model.Tuning;

import java.util.List;

public class TunerSelectTuningAdapter extends BaseAdapter {
    private Context context;
    List<Tuning> items;

    public TunerSelectTuningAdapter(Context context, List<Tuning> items){
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Tuning getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint({"ViewHolder", "InflateParams"}) View view = LayoutInflater.from(context).inflate(R.layout.item_tuner_select_tuning, null);


        TextView tuningName = view.findViewById(R.id.text_view_tuning_name);
        TextView pitches = view.findViewById(R.id.text_view_pitches);

        tuningName.setText(getItem(position).getName());
        pitches.setText(getItem(position).getPitchArray());

        return view;
    }
}
