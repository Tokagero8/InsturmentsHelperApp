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
import com.example.insturmentshelperapp.model.ChordWithBitmap;

import java.util.List;

public class LessonsInfoAdapter extends BaseAdapter {
    private Context context;
    List<ChordWithBitmap> items;

    public LessonsInfoAdapter(Context context, List<ChordWithBitmap> items){
        super();
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public ChordWithBitmap getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint({"ViewHolder", "InflateParams"}) View view = LayoutInflater.from(context).inflate(R.layout.item_lessons_chord, null);

        TextView chordType = view.findViewById(R.id.text_view_chord_type_name);
        ImageView chordImage = view.findViewById(R.id.image_view_chord);

        chordType.setText(getItem(position).getTone() + getItem(position).getType());
        chordImage.setImageBitmap(getItem(position).getBitmap());

        return view;
    }
}
