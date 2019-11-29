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
 * This class acts as a array adapter of the emotion book
 */

public class EmotionBookAdapter extends ArrayAdapter<MoodEvent> {

    private Context context;
    private int mResource;


    /**
     * this is a constructor that create the Emotion Book Adapter
     *
     * @param context  This is the current context. This value must never be null
     * @param resource This is the resource ID for a layout file containing a TextView to use when instantiating views
     * @param objects  This is the objects to represent in the ListView. This value must never be null
     */
    public EmotionBookAdapter(Context context, int resource, ArrayList<MoodEvent> objects) {
        super(context, resource, objects);
        this.context = context;
        mResource = resource;
    }

    /**
     * This return the view of the EmotionBookAdapter, it takes position , view and parent
     *
     * @param position    This is a position of the item within the adapter's data set of the item whose view we want
     * @param convertView This is the view. This value must never be null
     * @param parent      This is the view group. This value must never be null
     * @return return the view of the Emotion BookAdapter
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String var1_date = getItem(position).getDate().toString();//??-----
        String var2_time = getItem(position).getTime().toString();
        String var3_dist = getItem(position).getMoodType().getEmoji().toString();
        String moodName = getItem(position).getMoodType().getName().toString();

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(mResource, parent, false);

        //only the first 3 variables that will be displayed in the textView
        TextView tvDate = (TextView) convertView.findViewById(R.id.datelbl);
        TextView tvTime = (TextView) convertView.findViewById(R.id.timelbl);
        TextView tvEmotion = (TextView) convertView.findViewById(R.id.emotionlbl);
        tvDate.setText(var1_date);
        tvTime.setText(var2_time);
        tvEmotion.setText(var3_dist);

        if (moodName.equals("Anger")) {
            convertView.setBackgroundColor(Color.rgb(255, 140, 105));  //Salmon
        } else if (moodName.equals("Disgust")) {
            convertView.setBackgroundColor(Color.rgb(102, 221, 170)); //MediumAquamarine
        } else if (moodName.equals("Fear")) {
            convertView.setBackgroundColor(Color.rgb(150, 122, 233));
        } else if (moodName.equals("Happiness")) {
            convertView.setBackgroundColor(Color.rgb(233, 122, 205));  //pink
        } else if (moodName.equals("Sadness")) {
            convertView.setBackgroundColor(Color.rgb(135, 206, 250)); //LightSkyBlue
        } else if (moodName.equals("Surprise")) {
            convertView.setBackgroundColor(Color.rgb(255, 221, 84)); //yellow-orange
        }

        return convertView;
    }
}
