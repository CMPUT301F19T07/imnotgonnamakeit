package com.example.feelslikemonday.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.feelslikemonday.R;


import java.util.ArrayList;

/*
 *This class acts as a array adapter of the emotion book

 */

public class EmotionBookAdapter extends ArrayAdapter<MoodEvent> {

    private Context context;
    private int mResource;
    // constructor
    public EmotionBookAdapter(Context context, int resource, ArrayList<MoodEvent> objects) {
        super(context, resource, objects);
        this.context = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String var1_date = getItem(position).getDate().toString();//??-----
        String var2_time = getItem(position).getTime().toString();
        String var3_dist = getItem(position).getMoodType().getEmoji().toString();

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(mResource,parent,false);

        //only the first 3 variables that will be displayed in the textView
        TextView tvDate = (TextView) convertView.findViewById(R.id.datelbl);
        TextView tvTime = (TextView) convertView.findViewById(R.id.timelbl);
        TextView tvEmotion = (TextView) convertView.findViewById(R.id.emotionlbl);
        tvDate.setText(var1_date);
        tvTime.setText(var2_time);
        tvEmotion.setText(var3_dist);

        return convertView;
    }
}
