package com.example.feelslikemonday.ui.following;

import android.content.Context;
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

/*
 *This class acts as a array adapter for the followees
*/

public class FollowingPageAdapter extends ArrayAdapter<FolloweeMoodEvent> {

    private Context context;
    private int mResource;

    /**
     * This initializes follower request fragment
     * @param context
     * This is the current context. This value must never be null
     * @param resource
     * This is the resource ID for a layout file containing a TextView to use when instantiating views
     * @param objects
     * This is the objects to represent in the ListView. This value must never be null
     */
    public FollowingPageAdapter(Context context, int resource, ArrayList<FolloweeMoodEvent> objects) {
        super(context, resource, objects);
        this.context = context;
        mResource = resource;
    }


    /**
     * This  displays the data at the specified position in the data set
     * @param position
     * This is a position of the item within the adapter's data set of the item whose view we want
     * @param convertView
     * This is the view. This value must never be null
     * @param parent
     * This is the view group. This value must never be null
     * @return
     *      return the view of FollowingPage
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String followeeUsername = getItem(position).getUsername().toString();
        String datePosted = getItem(position).getRecentMood().getDate().toString();
        String timePosted = getItem(position).getRecentMood().getTime().toString();
        String mood = getItem(position).getRecentMood().getMoodType().getEmoji().toString();

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

        return convertView;
    }
}
