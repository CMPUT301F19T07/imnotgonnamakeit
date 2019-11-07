package com.example.feelslikemonday.ui.followrequest.ArraryAdapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.feelslikemonday.DAO.FollowPermissionCallback;
import com.example.feelslikemonday.DAO.FollowPermissionDAO;
import com.example.feelslikemonday.DAO.FollowRequestCallback;
import com.example.feelslikemonday.DAO.FollowRequestDAO;
import com.example.feelslikemonday.DAO.VoidCallback;
import com.example.feelslikemonday.R;
import com.example.feelslikemonday.model.FollowPermission;
import com.example.feelslikemonday.model.FollowRequest;
import com.example.feelslikemonday.model.User;
import com.example.feelslikemonday.ui.login.SignupActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class RequestList extends ArrayAdapter<String> {
    private Context context;
    private List<String> requesterUsernames;
    private List<String> followeeUsernames;
    private static FollowPermissionDAO DAO;
    private static FollowRequestDAO followRequestDAO;
    private SharedPreferences pref;
    private String myUserID;


    public RequestList(@NonNull Context context, ArrayList<String> users) {
        super(context, 0, users);
        this.requesterUsernames = users;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        DAO = FollowPermissionDAO.getInstance();
        followRequestDAO = FollowRequestDAO.getInstance();
        pref = getContext().getSharedPreferences(SignupActivity.PREFS_NAME, 0);
        myUserID = pref.getString(SignupActivity.USERNAME_KEY,null);

        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.content_follow_request, parent, false);
        }
        final String user = requesterUsernames.get(position);

        TextView userName = view.findViewById(R.id.username_text);
        userName.setText(user);

        Button acceptButton = view.findViewById(R.id.request_accept_button);
        Button rejectButton = view.findViewById(R.id.request_reject_button);

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("testListView", "clickAcceptButton");

                DAO.get(myUserID, new FollowPermissionCallback(){
                    @Override
                    public void onCallback(FollowPermission followPermission) {
                        if(followPermission.getFollowerUsername().equals(myUserID)){
                            followeeUsernames = followPermission.getFolloweeUsernames();
                            followeeUsernames.add(user);

                            DAO.createOrUpdate(myUserID, followPermission, new VoidCallback() {
                                @Override
                                public void onCallback() {
                                }
                            });
                        }

                    }
                }, null);

                followRequestDAO.get(myUserID, new FollowRequestCallback(){
                    @Override
                    public void onCallback(FollowRequest followRequest) {
                        if(followRequest.getRecipientUsername().equals(myUserID)){
                            requesterUsernames = followRequest.getRequesterUsernames();
                            requesterUsernames.remove(user);

                            followRequestDAO.createOrUpdate(myUserID, followRequest, new VoidCallback() {
                                @Override
                                public void onCallback() {
                                }
                            });
                            RequestList.this.remove(user);
                        }

                    }
                },null);

            }
        });

        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("testListView", "clickRejectButton");

                followRequestDAO.get(myUserID, new FollowRequestCallback(){
                    @Override
                    public void onCallback(FollowRequest followRequest) {
                        if(followRequest.getRecipientUsername().equals(myUserID)){
                            requesterUsernames = followRequest.getRequesterUsernames();
                            requesterUsernames.remove(user);

                            followRequestDAO.createOrUpdate(myUserID, followRequest, new VoidCallback() {
                                @Override
                                public void onCallback() {
                                }
                            });

                            RequestList.this.remove(user);

                        }

                    }
                },null);



            }
        });
        return view;



    }
}
