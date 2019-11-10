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
        import com.example.feelslikemonday.model.followeeMoodEvent;


        import java.util.ArrayList;

/*
 *This class acts as a array adapter of the emotion book

 */

public class FolloweeBookAdapter extends ArrayAdapter<followeeMoodEvent> {

    private Context context;
    private int mResource;
    // constructor
    public FolloweeBookAdapter(Context context, int resource, ArrayList<followeeMoodEvent> objects) {
        super(context, resource, objects);
        this.context = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String var0_fUser = getItem(position).getUsername().toString();

        String var1_date = getItem(position).getRecentMood().getDate().toString();
        String var2_time = getItem(position).getRecentMood().getTime().toString();
        String var3_dist = getItem(position).getRecentMood().getMoodType().getEmoji().toString();

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(mResource,parent,false);

        //only the first 3 variables that will be displayed in the textView
        TextView tvfUser = (TextView) convertView.findViewById(R.id.followee);
        TextView tvDate = (TextView) convertView.findViewById(R.id.fdatelbl);
        TextView tvTime = (TextView) convertView.findViewById(R.id.ftimelbl);
        TextView tvEmotion = (TextView) convertView.findViewById(R.id.femotionlbl);
        tvfUser.setText(var0_fUser);
        tvDate.setText(var1_date);
        tvTime.setText(var2_time);
        tvEmotion.setText(var3_dist);


        return convertView;
    }
}
