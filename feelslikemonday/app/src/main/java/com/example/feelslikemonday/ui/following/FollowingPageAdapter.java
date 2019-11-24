package com.example.feelslikemonday.ui.following;

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
import com.example.feelslikemonday.model.FolloweeMoodEvent;

import java.util.ArrayList;

/**
 *This class acts as a array adapter for the followee's on the following page
*/

public class FollowingPageAdapter extends ArrayAdapter<FolloweeMoodEvent> {

    private Context context;
    private int mResource;

    /**
     * This is a constructor that creates the Following Page Adapter
     * @param context
     * @param resource
     * @param objects
     */
    public FollowingPageAdapter(Context context, int resource, ArrayList<FolloweeMoodEvent> objects) {
        super(context, resource, objects);
        this.context = context;
        mResource = resource;
    }

    /**
     * This is view of FollowingPage Adapter, it takes position, convertView and parent
     * @param position
     * @param convertView
     * @param parent
     * @return the view of Following page Adapter
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String followeeUsername = getItem(position).getUsername().toString();
        String datePosted = getItem(position).getRecentMood().getDate().toString();
        String timePosted = getItem(position).getRecentMood().getTime().toString();
        String mood = getItem(position).getRecentMood().getMoodType().getEmoji().toString();
        String moodName = getItem(position).getRecentMood().getMoodType().getName().toString();

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(mResource,parent,false);

        //For every person you follow, 4 variables will be displayed in the listview (username, date, time, mood)
        TextView tvfUser = (TextView) convertView.findViewById(R.id.followee);
        TextView tvDate = (TextView) convertView.findViewById(R.id.fdatelbl);
        TextView tvTime = (TextView) convertView.findViewById(R.id.ftimelbl);
        TextView tvEmotion = (TextView) convertView.findViewById(R.id.femotionlbl);
        tvfUser.setText(followeeUsername);
        tvDate.setText(datePosted);
        tvTime.setText(timePosted);
        tvEmotion.setText(mood);

        if (moodName.equals("Anger")){
            convertView.setBackgroundColor(Color.rgb(255, 140, 105));  //DarkSalmon
        }
        else if (moodName.equals("Disgust")){
            convertView.setBackgroundColor(Color.rgb(102, 221, 170)); //MediumAquamarine
        }
        else if (moodName.equals("Fear")){
            convertView.setBackgroundColor(Color.rgb(150, 122, 233));
        }
        else if (moodName.equals("Happiness")){
            convertView.setBackgroundColor(Color.rgb(233, 122, 205));  //pink
        }
        else if (moodName.equals("Sadness")){
            convertView.setBackgroundColor(Color.rgb(135,206,250)); //LightSkyBlue
        }
        else if (moodName.equals("Surprise")){
            convertView.setBackgroundColor(Color.rgb(255, 221, 84)); //yellow-orange
        }

        return convertView;
    }
}
