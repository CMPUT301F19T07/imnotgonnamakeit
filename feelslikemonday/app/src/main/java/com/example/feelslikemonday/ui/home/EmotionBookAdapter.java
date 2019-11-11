package com.example.feelslikemonday.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.feelslikemonday.R;
import com.example.feelslikemonday.model.MoodEvent;


import java.util.ArrayList;

/**
 *This class acts as a array adapter of the emotion book
 */

public class EmotionBookAdapter extends ArrayAdapter<MoodEvent> {

    private Context context;
    private int mResource;


    /**
     * this is a constructor that create the Emotion Book Adapter
     * @param context
     * @param resource
     * @param objects
     */
    public EmotionBookAdapter(Context context, int resource, ArrayList<MoodEvent> objects) {
        super(context, resource, objects);
        this.context = context;
        mResource = resource;
    }

    /**
     * This return the view of the EmotionBookAdapter, it takes position , view and parent.
     * @param position
     * @param convertView
     * @param parent
     * @return the view of the Emotion BookAdapter
     */

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String var1_date = getItem(position).getDate().toString();//??-----
        String var2_time = getItem(position).getTime().toString();
        String var3_dist = getItem(position).getMoodType().getEmoji().toString();
        String moodname = getItem(position).getMoodType().getName().toString();

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(mResource,parent,false);

        //only the first 3 variables that will be displayed in the textView
        TextView tvDate = (TextView) convertView.findViewById(R.id.datelbl);
        TextView tvTime = (TextView) convertView.findViewById(R.id.timelbl);
        TextView tvEmotion = (TextView) convertView.findViewById(R.id.emotionlbl);
        tvDate.setText(var1_date);
        tvTime.setText(var2_time);
        tvEmotion.setText(var3_dist);

        if (moodname.equals("Anger")){
            convertView.setBackgroundColor(Color.rgb(255, 124, 84));  //orange-red
        }
        else if (moodname.equals("Disgust")){
            convertView.setBackgroundColor(Color.rgb(89, 207, 93)); // green
        }
        else if (moodname.equals("Fear")){
            convertView.setBackgroundColor(Color.rgb(142, 75, 209)); //purple
        }
        else if (moodname.equals("Happiness")){
            convertView.setBackgroundColor(Color.rgb(237, 26, 160));  //pink
        }
        else if (moodname.equals("Sadness")){
            convertView.setBackgroundColor(Color.rgb(66, 168, 227)); //blue
        }
        else if (moodname.equals("Surprise")){
            convertView.setBackgroundColor(Color.rgb(255, 221, 84)); //yellow-orange
        }

        return convertView;
    }
}
