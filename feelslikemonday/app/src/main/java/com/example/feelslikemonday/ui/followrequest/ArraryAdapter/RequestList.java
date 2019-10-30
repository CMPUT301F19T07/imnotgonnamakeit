package com.example.feelslikemonday.ui.followrequest.ArraryAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.feelslikemonday.R;
import com.example.feelslikemonday.model.User;

import java.util.ArrayList;

public class RequestList extends ArrayAdapter<User> {
    private ArrayList<User> users;
    private Context context;


    public RequestList(@NonNull Context context, ArrayList<User> users) {
        super(context, 0, users);
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.content_follow_request, parent, false);
        }
        User user = users.get(position);
        TextView userName = view.findViewById(R.id.username_text);
        userName.setText(user.getUsername());

        return view;
    }
}
