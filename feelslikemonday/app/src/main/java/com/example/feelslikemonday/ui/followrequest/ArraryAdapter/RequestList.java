package com.example.feelslikemonday.ui.followrequest.ArraryAdapter;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class RequestList extends ArrayAdapter<String> {
    private Context context;
    private List<String> requesterUsernames;
    private List<String> followeeUsernames;
    private static FollowPermissionDAO DAO;
    private static FollowRequestDAO followRequestDAO;
    private final CountDownLatch signal = new CountDownLatch(1);


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

                DAO.get("xiaole", new FollowPermissionCallback(){
                    @Override
                    public void onCallback(FollowPermission followPermission) {
                        if(followPermission.getFollowerUsername().equals("xiaole")){
                            followeeUsernames = followPermission.getFolloweeUsernames();
                            followeeUsernames.add(user);

                            DAO.createOrUpdate("xiaole", followPermission, new VoidCallback() {
                                @Override
                                public void onCallback() {
                                    signal.countDown();
                                }
                            });
                        }

                    }
                }, null);

                followRequestDAO.get("xiaole", new FollowRequestCallback(){
                    @Override
                    public void onCallback(FollowRequest followRequest) {
                        if(followRequest.getRecipientUsername().equals("xiaole")){
                            requesterUsernames = followRequest.getRequesterUsernames();
                            requesterUsernames.remove(user);

                            followRequestDAO.createOrUpdate("xiaole", followRequest, new VoidCallback() {
                                @Override
                                public void onCallback() {
                                    signal.countDown();
                                }
                            });
                        }

                    }
                },null);

            }
        });

        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("testListView", "clickRejectButton");

                followRequestDAO.get("xiaole", new FollowRequestCallback(){
                    @Override
                    public void onCallback(FollowRequest followRequest) {
                        if(followRequest.getRecipientUsername().equals("xiaole")){
                            requesterUsernames = followRequest.getRequesterUsernames();
                            requesterUsernames.remove(user);

                            followRequestDAO.createOrUpdate("xiaole", followRequest, new VoidCallback() {
                                @Override
                                public void onCallback() {
                                    signal.countDown();
                                }
                            });
                        }

                    }
                },null);

            }
        });
        return view;



    }
}
