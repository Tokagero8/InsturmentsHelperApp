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
import com.example.insturmentshelperapp.model.Instrument;

import java.util.List;

public class LessonsSettingsSelectInstrumentAdapter extends BaseAdapter {
    private Context context;
    List<Instrument> items;

    public LessonsSettingsSelectInstrumentAdapter(Context context, List<Instrument> items){
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Instrument getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint({"ViewHolder", "InflateParams"}) View view = LayoutInflater.from(context).inflate(R.layout.item_tuner_select_instrument, null);

        ImageView imageView = view.findViewById(R.id.image_view_instrument_icon);
        TextView textView = view.findViewById(R.id.text_view_instrument_name);

        int id = context.getResources().getIdentifier(getItem(position).getImage(), "drawable", context.getPackageName());
        imageView.setImageResource(id);
        textView.setText(getItem(position).getName());

        return view;
    }
}
