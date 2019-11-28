package com.example.feelslikemonday.ui.followrequest.ArraryAdapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

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
import com.example.feelslikemonday.ui.login.SignupActivity;

import java.util.ArrayList;
import java.util.List;


/**
 *This class acts as a array adapter for the request list
 */
public class RequestList extends ArrayAdapter<String> {
    private Context context;
    private List<String> requesterUsernames;
    private List<String> followeeUsernames;
    private static FollowPermissionDAO followPermissionDAO;
    private static FollowRequestDAO followRequestDAO;
    private SharedPreferences pref;
    private String myUserID;

    /**
     * This constructor initializes follower request fragment
     * @param context
     * This is the current context. This value must never be null
     * @param users
     * This is the objects to represent in the ListView. This value must never be null
     */
    public RequestList(@NonNull Context context, ArrayList<String> users) {
        super(context, 0, users);
        this.requesterUsernames = users;
        this.context = context;
    }

    /**
     * This gets a View that displays the data at the specified position in the data set
     * @param position
     * This is the position of the item within the adapter's data set of the item whose view we want
     * @param convertView
     * This is the view. This value must never be null
     * @param parent
     * This is the view group. This value must never be null
     * @return
     *      return a View of requester corresponding to the data at the specified position
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        followPermissionDAO = FollowPermissionDAO.getInstance();
        followRequestDAO = FollowRequestDAO.getInstance();
        pref = getContext().getSharedPreferences(SignupActivity.PREFS_NAME, 0);
        myUserID = pref.getString(SignupActivity.USERNAME_KEY,null);

        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.content_follow_request, parent, false);
        }
        final String requesterUsername = requesterUsernames.get(position);

        TextView userName = view.findViewById(R.id.username_text);
        userName.setText(requesterUsername);

        Button acceptButton = view.findViewById(R.id.request_accept_button);
        Button rejectButton = view.findViewById(R.id.request_reject_button);

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("testListView", "clickAcceptButton");

                //swap the following
                followPermissionDAO.get(requesterUsername, new FollowPermissionCallback(){
                    @Override
                    public void onCallback(FollowPermission followPermission) {
                        if(followPermission.getFollowerUsername().equals(requesterUsername)){
                            followeeUsernames = followPermission.getFolloweeUsernames();
                            followeeUsernames.add( myUserID);

                            followPermissionDAO.createOrUpdate(requesterUsername, followPermission, new VoidCallback() {
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
                            requesterUsernames.remove(requesterUsername);

                            followRequestDAO.createOrUpdate(myUserID, followRequest, new VoidCallback() {
                                @Override
                                public void onCallback() {
                                }
                            });
                            RequestList.this.remove(requesterUsername);
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
                            requesterUsernames.remove(requesterUsername);

                            followRequestDAO.createOrUpdate(myUserID, followRequest, new VoidCallback() {
                                @Override
                                public void onCallback() {
                                }
                            });
                            RequestList.this.remove(requesterUsername);
                        }
                    }
                },null);
            }
        });
        return view;
    }
}
